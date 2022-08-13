package com.adedom.authentication.presentation.view_model

import com.adedom.authentication.presentation.event.RegisterUiEvent
import com.adedom.authentication.presentation.state.RegisterUiState
import com.adedom.core.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RegisterViewModel : BaseViewModel<RegisterUiState, RegisterUiEvent>(RegisterUiState.Initial) {

    private val _form = MutableStateFlow(RegisterUiState.RegisterForm())
    val form: StateFlow<RegisterUiState.RegisterForm> = _form.asStateFlow()

    fun setName(name: String) {
        _form.update {
            it.copy(name = name)
        }
    }

    fun setEmail(email: String) {
        _form.update {
            it.copy(email = email)
        }
    }

    fun setMobileNo(mobileNo: String) {
        _form.update {
            it.copy(mobileNo = mobileNo)
        }
    }

    fun setAddress(address: String) {
        _form.update {
            it.copy(address = address)
        }
    }

    fun setPassword(password: String) {
        _form.update {
            it.copy(password = password)
        }
    }

    fun setConfirmPassword(confirmPassword: String) {
        _form.update {
            it.copy(confirmPassword = confirmPassword)
        }
    }

    fun onLoginEvent() {
        launch {
            val event = RegisterUiEvent.Login
            _uiEvent.emit(event)
        }
    }

    fun onRegisterEvent() {}
}