package com.adedom.main.presentation.state

import com.adedom.main.domain.models.UserProfileModel

sealed interface MainUiState {
    object Initial : MainUiState

    data class ShowUserProfile(
        val userProfile: UserProfileModel,
    ) : MainUiState
}