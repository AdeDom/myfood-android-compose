package com.adedom.user_profile.presentation.view_model

import com.adedom.core.data.Resource2
import com.adedom.myfood.data.models.base.BaseError
import com.adedom.ui_components.base.BaseViewModel
import com.adedom.user_profile.domain.models.UserProfileModel
import com.adedom.user_profile.domain.use_cases.FetchUserProfileUseCase
import com.adedom.user_profile.domain.use_cases.GetUserProfileUseCase
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
                setState { copy(userProfile = userProfile) }
            }
            .launchIn(this)
    }

    private fun callUserProfile() {
        launch {
            val resource = fetchUserProfileUseCase()
            when (resource) {
                is Resource2.Success -> {}
                is Resource2.Error -> {
                    setState {
                        copy(dialog = UserProfileUiState.Dialog.Error(resource.error))
                    }
                }
                is Resource2.RefreshTokenExpired -> {
                    setState {
                        copy(dialog = UserProfileUiState.Dialog.RefreshTokenExpired(resource.error))
                    }
                }
            }
        }
    }

    override fun dispatch(event: UserProfileUiEvent) {
        launch {
            when (event) {
                UserProfileUiEvent.DismissErrorDialog -> {
                    setState { copy(dialog = null) }
                }
            }
        }
    }
}