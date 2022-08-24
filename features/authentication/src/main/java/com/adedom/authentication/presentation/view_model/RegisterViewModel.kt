package com.adedom.authentication.presentation.view_model

import androidx.lifecycle.viewModelScope
import com.adedom.authentication.presentation.event.RegisterUiEvent
import com.adedom.authentication.presentation.state.RegisterUiState
import com.adedom.ui_components.base.BaseViewModel
import kotlinx.coroutines.launch

class RegisterViewModel : BaseViewModel<RegisterUiState, RegisterUiEvent>(RegisterUiState()) {

    fun setName(name: String) {
        uiState = uiState.copy(name = name)
    }

    fun setEmail(email: String) {
        uiState = uiState.copy(email = email)
    }

    fun setMobileNo(mobileNo: String) {
        uiState = uiState.copy(mobileNo = mobileNo)
    }

    fun setAddress(address: String) {
        uiState = uiState.copy(address = address)
    }

    fun setPassword(password: String) {
        uiState = uiState.copy(password = password)
    }

    fun setConfirmPassword(confirmPassword: String) {
        uiState = uiState.copy(confirmPassword = confirmPassword)
    }

    fun onLoginEvent() {
        viewModelScope.launch {
            val event = RegisterUiEvent.Login
            _uiEvent.emit(event)
        }
    }

    fun onRegisterEvent() {}
}