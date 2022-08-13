package com.adedom.main.presentation.event

sealed interface MainUiEvent {
    object Logout : MainUiEvent
}