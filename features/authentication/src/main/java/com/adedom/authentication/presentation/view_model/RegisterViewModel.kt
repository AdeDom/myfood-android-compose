package com.adedom.authentication.presentation.view_model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.adedom.authentication.presentation.event.RegisterUiEvent
import com.adedom.authentication.presentation.state.RegisterUiState
import com.adedom.core.base.BaseViewModel

class RegisterViewModel : BaseViewModel<RegisterUiState, RegisterUiEvent>(RegisterUiState.Initial) {

    var form by mutableStateOf(RegisterUiState.RegisterForm())
        private set

    fun setName(name: String) {
        form = form.copy(name = name)
    }

    fun setEmail(email: String) {
        form = form.copy(email = email)
    }

    fun setMobileNo(mobileNo: String) {
        form = form.copy(mobileNo = mobileNo)
    }

    fun setAddress(address: String) {
        form = form.copy(address = address)
    }

    fun setPassword(password: String) {
        form = form.copy(password = password)
    }

    fun setConfirmPassword(confirmPassword: String) {
        form = form.copy(confirmPassword = confirmPassword)
    }

    fun onLoginEvent() {
        launch {
            val event = RegisterUiEvent.Login
            _uiEvent.emit(event)
        }
    }

    fun onRegisterEvent() {}
}