package com.adedom.splash_screen.presentation.event

sealed interface SplashScreenUiEvent {
    object Authentication : SplashScreenUiEvent
    object UnAuthentication : SplashScreenUiEvent
}