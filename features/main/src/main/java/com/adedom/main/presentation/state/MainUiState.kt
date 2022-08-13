package com.adedom.main.presentation.state

sealed interface MainUiState {
    object Initial : MainUiState
//    data class ShowUserProfile(
//        val userProfile: UserProfileModel,
//    ) : MainUiState
}