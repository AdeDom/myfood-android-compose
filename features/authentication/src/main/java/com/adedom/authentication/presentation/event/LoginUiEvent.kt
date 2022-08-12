package com.adedom.authentication.presentation.event

sealed interface LoginUiEvent {
    object Register : LoginUiEvent
    object LoginSuccess : LoginUiEvent
}