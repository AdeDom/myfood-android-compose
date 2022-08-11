package com.adedom.splash_screen.presentation.splash_screen.state

sealed interface SplashScreenUiState {
    object Initial : SplashScreenUiState
    object Authentication : SplashScreenUiState
    object UnAuthentication : SplashScreenUiState
}