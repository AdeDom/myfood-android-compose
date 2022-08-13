package com.adedom.authentication.presentation.view_model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.adedom.authentication.domain.use_cases.LoginUseCase
import com.adedom.authentication.domain.use_cases.ValidateEmailUseCase
import com.adedom.authentication.domain.use_cases.ValidatePasswordUseCase
import com.adedom.authentication.presentation.event.LoginUiEvent
import com.adedom.authentication.presentation.state.LoginUiState
import com.adedom.core.base.BaseViewModel
import com.adedom.core.utils.Resource

class LoginViewModel(
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val loginUseCase: LoginUseCase,
) : BaseViewModel<LoginUiState, LoginUiEvent>(LoginUiState.Initial) {

    var form by mutableStateOf(LoginUiState.LoginForm())
        private set

    fun setEmail(email: String) {
        form = form.copy(email = email)
    }

    fun setPassword(password: String) {
        form = form.copy(password = password)
    }

    fun onValidateEmail() {
        val isValidateEmail = validateEmailUseCase(form.email)
        val isValidatePassword = validatePasswordUseCase(form.password)
        uiState = LoginUiState.ValidateEmail(
            isError = !isValidateEmail,
            isLogin = isValidateEmail && isValidatePassword,
        )
    }

    fun onValidatePassword() {
        val isValidateEmail = validateEmailUseCase(form.email)
        val isValidatePassword = validatePasswordUseCase(form.password)
        uiState = LoginUiState.ValidatePassword(
            isError = !isValidatePassword,
            isLogin = isValidateEmail && isValidatePassword,
        )
    }

    fun onLoginEvent() {
        launch {
            uiState = LoginUiState.Loading

            val email = form.email
            val password = form.password
            val resource = loginUseCase(email, password)
            when (resource) {
                is Resource.Success -> {
                    val event = LoginUiEvent.LoginSuccess
                    _uiEvent.emit(event)
                }
                is Resource.Error -> {
                    uiState = LoginUiState.LoginError(resource.error)
                }
            }
        }
    }

    fun onRegisterEvent() {
        launch {
            val event = LoginUiEvent.Register
            _uiEvent.emit(event)
        }
    }
}