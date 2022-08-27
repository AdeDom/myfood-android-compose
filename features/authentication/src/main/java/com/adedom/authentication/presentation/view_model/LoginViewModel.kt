package com.adedom.authentication.presentation.view_model

import androidx.lifecycle.viewModelScope
import com.adedom.authentication.domain.use_cases.LoginUseCase
import com.adedom.authentication.domain.use_cases.ValidateEmailUseCase
import com.adedom.authentication.domain.use_cases.ValidatePasswordUseCase
import com.adedom.core.utils.Resource
import com.adedom.myfood.data.models.base.BaseError
import com.adedom.ui_components.base.BaseViewModel
import kotlinx.coroutines.launch

data class LoginUiState(
    val isErrorEmail: Boolean = false,
    val isErrorPassword: Boolean = false,
    val isLogin: Boolean = false,
    val isLoading: Boolean = false,
    val error: BaseError? = null,
    val email: String = "",
    val password: String = "",
)

sealed interface LoginUiEvent {
    object NavRegister : LoginUiEvent
    object NavMain : LoginUiEvent
}

sealed interface LoginUiAction {
    data class SetEmail(val value: String) : LoginUiAction
    data class SetPassword(val value: String) : LoginUiAction
    object Submit : LoginUiAction
    object HideErrorDialog : LoginUiAction
    object NavRegister : LoginUiAction
}

class LoginViewModel(
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val loginUseCase: LoginUseCase,
) : BaseViewModel<LoginUiState, LoginUiEvent>(LoginUiState()) {

    fun dispatch(action: LoginUiAction) {
        viewModelScope.launch(exceptionHandler) {
            when (action) {
                is LoginUiAction.SetEmail -> {
                    val isValidateEmail = validateEmailUseCase(email = action.value)
                    val isValidatePassword = validatePasswordUseCase(uiState.password)
                    uiState = uiState.copy(
                        email = action.value,
                        isErrorEmail = !isValidateEmail,
                        isLogin = isValidateEmail && isValidatePassword,
                    )
                }
                is LoginUiAction.SetPassword -> {
                    val isValidateEmail = validateEmailUseCase(uiState.email)
                    val isValidatePassword = validatePasswordUseCase(action.value)
                    uiState = uiState.copy(
                        password = action.value,
                        isErrorPassword = !isValidatePassword,
                        isLogin = isValidateEmail && isValidatePassword,
                    )
                }
                LoginUiAction.Submit -> {
                    uiState = uiState.copy(
                        isLoading = true,
                        isLogin = false,
                        error = null,
                    )

                    val email = uiState.email
                    val password = uiState.password
                    val resource = loginUseCase(email, password)
                    when (resource) {
                        is Resource.Success -> {
                            _uiEvent.emit(LoginUiEvent.NavMain)
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
                LoginUiAction.HideErrorDialog -> {
                    uiState = uiState.copy(error = null)
                }
                LoginUiAction.NavRegister -> {
                    _uiEvent.emit(LoginUiEvent.NavRegister)
                }
            }
        }
    }
}