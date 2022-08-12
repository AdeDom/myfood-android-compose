package com.adedom.welcome.presentation.event

sealed interface WelcomeUiEvent {
    object Login : WelcomeUiEvent
    object Register : WelcomeUiEvent
    data class Skip(val skip: Unit) : WelcomeUiEvent
}