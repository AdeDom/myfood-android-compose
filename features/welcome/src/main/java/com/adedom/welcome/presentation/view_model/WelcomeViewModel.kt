package com.adedom.welcome.presentation.view_model

import androidx.lifecycle.viewModelScope
import com.adedom.ui_components.base.BaseViewModel
import com.adedom.welcome.domain.use_cases.WelcomeGuestRoleUseCase
import com.adedom.welcome.presentation.event.WelcomeUiEvent
import com.adedom.welcome.presentation.state.WelcomeUiState
import kotlinx.coroutines.launch

class WelcomeViewModel(
    private val welcomeGuestRoleUseCase: WelcomeGuestRoleUseCase,
) : BaseViewModel<WelcomeUiState, WelcomeUiEvent>(WelcomeUiState.Initial) {

    fun onLoginEvent() {
        viewModelScope.launch {
            val event = WelcomeUiEvent.Login
            _uiEvent.emit(event)
        }
    }

    fun onRegisterEvent() {
        viewModelScope.launch {
            val event = WelcomeUiEvent.Register
            _uiEvent.emit(event)
        }
    }

    fun onSkipEvent() {
        viewModelScope.launch {
            val event = WelcomeUiEvent.Skip(welcomeGuestRoleUseCase())
            _uiEvent.emit(event)
        }
    }
}