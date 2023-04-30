package com.adedom.user_profile.presentation.view_model

import androidx.lifecycle.viewModelScope
import com.adedom.core.utils.ApiServiceException
import com.adedom.core.utils.RefreshTokenExpiredException
import com.adedom.ui_components.base.BaseViewModel
import com.adedom.user_profile.domain.models.UserProfileModel
import com.adedom.user_profile.domain.use_cases.FetchUserProfileUseCase
import com.adedom.user_profile.domain.use_cases.GetUserProfileUseCase
import com.myfood.server.data.models.base.BaseError
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

data class UserProfileUiState(
    val userProfile: UserProfileModel? = null,
    val dialog: Dialog? = null,
) {
    sealed interface Dialog {
        data class Error(val error: BaseError) : Dialog
        data class RefreshTokenExpired(val error: BaseError) : Dialog
    }
}

sealed interface UserProfileUiEvent {
    object DismissErrorDialog : UserProfileUiEvent
}

class UserProfileViewModel(
    private val fetchUserProfileUseCase: FetchUserProfileUseCase,
    private val getUserProfileUseCase: GetUserProfileUseCase,
) : BaseViewModel<UserProfileUiEvent, UserProfileUiState>(UserProfileUiState()) {

    init {
        initUserProfile()
        callUserProfile()
    }

    private fun initUserProfile() {
        getUserProfileUseCase()
            .onEach { userProfile ->
                emit { copy(userProfile = userProfile) }
            }
            .launchIn(viewModelScope)
    }

    private fun callUserProfile() {
        viewModelScope.launch {
            try {
                fetchUserProfileUseCase()
            } catch (exception: ApiServiceException) {
                emit {
                    copy(
                        dialog = UserProfileUiState.Dialog.Error(
                            exception.toBaseError(),
                        ),
                    )
                }
            } catch (exception: RefreshTokenExpiredException) {
                emit {
                    copy(
                        dialog = UserProfileUiState.Dialog.RefreshTokenExpired(
                            exception.toBaseError(),
                        ),
                    )
                }
            }
        }
    }

    override fun onEvent(event: UserProfileUiEvent) {
        viewModelScope.launch {
            when (event) {
                UserProfileUiEvent.DismissErrorDialog -> {
                    emit { copy(dialog = null) }
                }
            }
        }
    }
}