package com.adedom.authentication.presentation.event

sealed interface RegisterUiEvent {
    object Login : RegisterUiEvent
}