package com.adedom.authentication.presentation.view_model

import com.adedom.authentication.domain.use_cases.LoginUseCase
import com.adedom.authentication.domain.use_cases.ValidateEmailUseCase
import com.adedom.authentication.domain.use_cases.ValidatePasswordUseCase
import com.adedom.core.utils.Resource
import com.adedom.myfood.data.models.base.BaseError
import com.adedom.ui_components.base.BaseMvi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
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

sealed interface LoginUiAction {
    data class SetEmail(val value: String) : LoginUiAction
    data class SetPassword(val value: String) : LoginUiAction
    object Submit : LoginUiAction
    object HideErrorDialog : LoginUiAction
}

class LoginViewModel(
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val loginUseCase: LoginUseCase,
) : BaseMvi<LoginUiState, LoginUiAction>(LoginUiState()) {

    private val _nav = Channel<Unit>()
    val nav: Flow<Unit> = _nav.receiveAsFlow()

    override fun dispatch(action: LoginUiAction) {
        launch {
            when (action) {
                is LoginUiAction.SetEmail -> {
                    val isValidateEmail = validateEmailUseCase(email = action.value)
                    val isValidatePassword = validatePasswordUseCase(uiState.password)
                    setState {
                        copy(
                            email = action.value,
                            isErrorEmail = !isValidateEmail,
                            isLogin = isValidateEmail && isValidatePassword,
                        )
                    }
                }
                is LoginUiAction.SetPassword -> {
                    val isValidateEmail = validateEmailUseCase(uiState.email)
                    val isValidatePassword = validatePasswordUseCase(action.value)
                    setState {
                        copy(
                            password = action.value,
                            isErrorPassword = !isValidatePassword,
                            isLogin = isValidateEmail && isValidatePassword,
                        )
                    }
                }
                LoginUiAction.Submit -> {
                    setState {
                        copy(
                            isLoading = true,
                            isLogin = false,
                            error = null,
                        )
                    }

                    val email = uiState.email
                    val password = uiState.password
                    val resource = loginUseCase(email, password)
                    when (resource) {
                        is Resource.Success -> {
                            _nav.send(resource.data)
                        }
                        is Resource.Error -> {
                            setState {
                                copy(
                                    error = resource.error,
                                    isLoading = false,
                                    isLogin = true,
                                )
                            }
                        }
                    }
                }
                LoginUiAction.HideErrorDialog -> {
                    setState { copy(error = null) }
                }
            }
        }
    }
}