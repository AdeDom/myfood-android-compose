package com.adedom.user_profile.presentation.view_model

import com.adedom.core.utils.Resource2
import com.adedom.myfood.data.models.base.BaseError
import com.adedom.ui_components.base.BaseMvi
import com.adedom.user_profile.domain.models.UserProfileModel
import com.adedom.user_profile.domain.use_cases.FetchUserProfileUseCase
import com.adedom.user_profile.domain.use_cases.GetUserProfileUseCase
import kotlinx.coroutines.launch

data class UserProfileUiState(
    val userProfile: UserProfileModel? = null,
    val error: BaseError? = null,
    val refreshTokenExpired: BaseError? = null,
)

sealed interface UserProfileUiAction {
    object DismissErrorDialog : UserProfileUiAction
}

class UserProfileViewModel(
    private val fetchUserProfileUseCase: FetchUserProfileUseCase,
    private val getUserProfileUseCase: GetUserProfileUseCase,
) : BaseMvi<UserProfileUiState, UserProfileUiAction>(UserProfileUiState()) {

    init {
        initUserProfile()
        callUserProfile()
    }

    private fun initUserProfile() {
        launch {
            getUserProfileUseCase().collect { userProfile ->
                setState { copy(userProfile = userProfile) }
            }
        }
    }

    private fun callUserProfile() {
        launch {
            val resource = fetchUserProfileUseCase()
            when (resource) {
                is Resource2.Success -> {}
                is Resource2.Error -> {
                    setState { copy(error = resource.error) }
                }
                is Resource2.RefreshTokenExpired -> {
                    setState { copy(refreshTokenExpired = resource.error) }
                }
            }
        }
    }

    override fun dispatch(action: UserProfileUiAction) {
        launch {
            when (action) {
                UserProfileUiAction.DismissErrorDialog -> {
                    setState { copy(error = null) }
                }
            }
        }
    }
}