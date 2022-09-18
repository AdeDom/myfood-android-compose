package com.adedom.authentication.presentation.view_model

import com.adedom.authentication.domain.use_cases.LoginUseCase
import com.adedom.authentication.domain.use_cases.ValidateEmailUseCase
import com.adedom.authentication.domain.use_cases.ValidatePasswordUseCase
import com.adedom.core.data.Resource
import com.adedom.myfood.data.models.base.BaseError
import com.adedom.ui_components.base.BaseViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

data class LoginUiState(
    val isErrorEmail: Boolean = false,
    val isErrorPassword: Boolean = false,
    val isLogin: Boolean = false,
    val email: String = "",
    val password: String = "",
    val dialog: Dialog? = null,
) {
    sealed interface Dialog {
        object Loading : Dialog
        data class Error(val error: BaseError) : Dialog
    }
}

sealed interface LoginUiEvent {
    data class SetEmail(val value: String) : LoginUiEvent
    data class SetPassword(val value: String) : LoginUiEvent
    object Submit : LoginUiEvent
    object HideErrorDialog : LoginUiEvent
}

class LoginViewModel(
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val loginUseCase: LoginUseCase,
) : BaseViewModel<LoginUiEvent, LoginUiState>(LoginUiState()) {

    private val _nav = Channel<Unit>()
    val nav: Flow<Unit> = _nav.receiveAsFlow()

    override fun dispatch(event: LoginUiEvent) {
        launch {
            when (event) {
                is LoginUiEvent.SetEmail -> {
                    val isValidateEmail = validateEmailUseCase(email = event.value)
                    val isValidatePassword = validatePasswordUseCase(uiState.password)
                    setState {
                        copy(
                            email = event.value,
                            isErrorEmail = !isValidateEmail,
                            isLogin = isValidateEmail && isValidatePassword,
                        )
                    }
                }
                is LoginUiEvent.SetPassword -> {
                    val isValidateEmail = validateEmailUseCase(uiState.email)
                    val isValidatePassword = validatePasswordUseCase(event.value)
                    setState {
                        copy(
                            password = event.value,
                            isErrorPassword = !isValidatePassword,
                            isLogin = isValidateEmail && isValidatePassword,
                        )
                    }
                }
                LoginUiEvent.Submit -> {
                    setState {
                        copy(
                            isLogin = false,
                            dialog = LoginUiState.Dialog.Loading,
                        )
                    }

                    val email = uiState.email
                    val password = uiState.password
                    val resource = loginUseCase(email, password)
                    when (resource) {
                        is Resource.Success -> {
                            _nav.send(Unit)
                        }
                        is Resource.Error -> {
                            setState {
                                copy(
                                    isLogin = true,
                                    dialog = LoginUiState.Dialog.Error(resource.error),
                                )
                            }
                        }
                    }
                }
                LoginUiEvent.HideErrorDialog -> {
                    setState { copy(dialog = null) }
                }
            }
        }
    }
}