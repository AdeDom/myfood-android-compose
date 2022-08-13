package com.adedom.authentication.presentation.view_model

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
) : BaseViewModel<LoginUiState, LoginUiEvent>(LoginUiState()) {

    fun setEmail(email: String) {
        uiState = uiState.copy(email = email)
    }

    fun setPassword(password: String) {
        uiState = uiState.copy(password = password)
    }

    fun onHideErrorDialog() {
        uiState = uiState.copy(error = null)
    }

    fun onValidateEmail() {
        val isValidateEmail = validateEmailUseCase(uiState.email)
        val isValidatePassword = validatePasswordUseCase(uiState.password)
        uiState = uiState.copy(
            isErrorEmail = !isValidateEmail,
            isLogin = isValidateEmail && isValidatePassword,
        )
    }

    fun onValidatePassword() {
        val isValidateEmail = validateEmailUseCase(uiState.email)
        val isValidatePassword = validatePasswordUseCase(uiState.password)
        uiState = uiState.copy(
            isErrorPassword = !isValidatePassword,
            isLogin = isValidateEmail && isValidatePassword,
        )
    }

    fun onLoginEvent() {
        launch {
            uiState = uiState.copy(
                isLoading = true,
                isLogin = false,
            )

            val email = uiState.email
            val password = uiState.password
            val resource = loginUseCase(email, password)
            when (resource) {
                is Resource.Success -> {
                    val event = LoginUiEvent.LoginSuccess
                    _uiEvent.emit(event)
                }
                is Resource.Error -> {
                    uiState = uiState.copy(
                        error = resource.error,
                        isLoading = false,
                        isLogin = true,
                    )
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