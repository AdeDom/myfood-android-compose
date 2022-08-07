package com.adedom.myfood.presentation.authentication.view_model

import com.adedom.data.utils.Resource
import com.adedom.domain.use_cases.login.LoginUseCase
import com.adedom.domain.use_cases.validate.ValidateEmailUseCase
import com.adedom.domain.use_cases.validate.ValidatePasswordUseCase
import com.adedom.myfood.base.BaseViewModel
import com.adedom.myfood.presentation.authentication.event.LoginUiEvent
import com.adedom.myfood.presentation.authentication.state.LoginUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel(
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val loginUseCase: LoginUseCase,
) : BaseViewModel<LoginUiState, LoginUiEvent>(LoginUiState.Initial) {

    private val _form = MutableStateFlow(LoginUiState.LoginForm())
    val form: StateFlow<LoginUiState.LoginForm> = _form.asStateFlow()

    fun setEmail(email: String) {
        _form.update {
            it.copy(email = email)
        }
    }

    fun setPassword(password: String) {
        _form.update {
            it.copy(password = password)
        }
    }

    fun onValidateEmail() {
        _uiState.update {
            val isValidateEmail = validateEmailUseCase(_form.value.email)
            val isValidatePassword = validatePasswordUseCase(_form.value.password)
            LoginUiState.ValidateEmail(
                isError = !isValidateEmail,
                isLogin = isValidateEmail && isValidatePassword,
            )
        }
    }

    fun onValidatePassword() {
        _uiState.update {
            val isValidateEmail = validateEmailUseCase(_form.value.email)
            val isValidatePassword = validatePasswordUseCase(_form.value.password)
            LoginUiState.ValidatePassword(
                isError = !isValidatePassword,
                isLogin = isValidateEmail && isValidatePassword,
            )
        }
    }

    fun onLoginEvent() {
        launch {
            _uiState.update {
                LoginUiState.Loading
            }

            val email = _form.value.email
            val password = _form.value.password
            val resource = loginUseCase(email, password)
            when (resource) {
                is Resource.Success -> {
                    val event = LoginUiEvent.LoginSuccess
                    _uiEvent.emit(event)
                }
                is Resource.Error -> {
                    _uiState.update {
                        LoginUiState.LoginError(resource.error)
                    }
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