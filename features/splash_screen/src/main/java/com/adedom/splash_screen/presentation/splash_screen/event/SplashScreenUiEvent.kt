package com.adedom.splash_screen.presentation.splash_screen.event

sealed interface SplashScreenUiEvent {
    object Authentication : SplashScreenUiEvent
    object UnAuthentication : SplashScreenUiEvent
}