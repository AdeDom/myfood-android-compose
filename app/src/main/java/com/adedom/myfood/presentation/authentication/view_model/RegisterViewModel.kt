package com.adedom.myfood.presentation.authentication.view_model

import com.adedom.myfood.base.BaseViewModel
import com.adedom.myfood.presentation.authentication.event.RegisterUiEvent
import com.adedom.myfood.presentation.authentication.state.RegisterUiState

class RegisterViewModel(
) : BaseViewModel<RegisterUiState, RegisterUiEvent>(RegisterUiState.Initial) {

    fun onLoginEvent() {
        launch {
            val event = RegisterUiEvent.Login
            _uiEvent.emit(event)
        }
    }
}