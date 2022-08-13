package com.adedom.authentication.presentation.view_model

import com.adedom.authentication.presentation.event.RegisterUiEvent
import com.adedom.authentication.presentation.state.RegisterUiState
import com.adedom.core.base.BaseViewModel

class RegisterViewModel : BaseViewModel<RegisterUiState, RegisterUiEvent>(RegisterUiState.Initial) {

    fun onLoginEvent() {
        launch {
            val event = RegisterUiEvent.Login
            _uiEvent.emit(event)
        }
    }
}