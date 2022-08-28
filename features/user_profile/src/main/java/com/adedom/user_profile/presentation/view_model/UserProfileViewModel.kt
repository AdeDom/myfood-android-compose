package com.adedom.user_profile.presentation.view_model

import com.adedom.core.utils.Resource
import com.adedom.myfood.data.models.base.BaseError
import com.adedom.ui_components.base.BaseViewModel
import com.adedom.user_profile.domain.models.UserProfileModel
import com.adedom.user_profile.domain.use_cases.FetchUserProfileUseCase
import com.adedom.user_profile.domain.use_cases.GetUserProfileUseCase
import kotlinx.coroutines.launch

data class UserProfileUiState(
    val userProfile: UserProfileModel? = null,
    val error: BaseError? = null,
)

sealed interface UserProfileUiEvent {
    object BackPressed : UserProfileUiEvent
}

sealed interface UserProfileUiAction {
    object BackPressed : UserProfileUiAction
    object DismissErrorDialog : UserProfileUiAction
}

class UserProfileViewModel(
    private val fetchUserProfileUseCase: FetchUserProfileUseCase,
    private val getUserProfileUseCase: GetUserProfileUseCase,
) : BaseViewModel<UserProfileUiState, UserProfileUiEvent, UserProfileUiAction>(
    UserProfileUiState()
) {

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
                is Resource.Success -> {}
                is Resource.Error -> {
                    setState { copy(error = resource.error) }
                }
            }
        }
    }

    override fun dispatch(action: UserProfileUiAction) {
        launch {
            when (action) {
                UserProfileUiAction.BackPressed -> {
                    setEvent(UserProfileUiEvent.BackPressed)
                }
                UserProfileUiAction.DismissErrorDialog -> {
                    setState { copy(error = null) }
                }
            }
        }
    }
}