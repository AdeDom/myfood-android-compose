package com.adedom.welcome.presentation.view_model

import com.adedom.core.base.BaseViewModel
import com.adedom.welcome.domain.use_cases.WelcomeGuestRoleUseCase
import com.adedom.welcome.presentation.event.WelcomeUiEvent
import com.adedom.welcome.presentation.state.WelcomeUiState

class WelcomeViewModel(
    private val welcomeGuestRoleUseCase: WelcomeGuestRoleUseCase,
) : BaseViewModel<WelcomeUiState, WelcomeUiEvent>(WelcomeUiState.Initial) {

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
            val event = WelcomeUiEvent.Skip(welcomeGuestRoleUseCase())
            _uiEvent.emit(event)
        }
    }
}