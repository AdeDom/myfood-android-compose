package com.adedom.welcome.presentation.view_model

import com.adedom.core.base.BaseViewModel
import com.adedom.welcome.presentation.event.WelcomeUiEvent
import com.adedom.welcome.presentation.state.WelcomeUiState

class WelcomeViewModel : BaseViewModel<WelcomeUiState, WelcomeUiEvent>(WelcomeUiState.Initial) {

    fun onLoginEvent() {
        launch {
            val event = WelcomeUiEvent.Login
            _uiEvent.emit(event)
        }
    }

    fun onRegisterEvent() {
        launch {
            val event = WelcomeUiEvent.Register
            _uiEvent.emit(event)
        }
    }

    fun onSkipEvent() {
        launch {
//            val event = WelcomeUiEvent.Skip(welcomeGuestRoleUseCase())
            val event = WelcomeUiEvent.Skip(Unit)
            _uiEvent.emit(event)
        }
    }
}