package com.adedom.myfood.presentation.welcome.view_model

import com.adedom.domain.use_cases.welcome.WelcomeGuestRoleUseCase
import com.adedom.myfood.base.BaseViewModel
import com.adedom.myfood.presentation.welcome.event.WelcomeUiEvent
import com.adedom.myfood.presentation.welcome.state.WelcomeUiState

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