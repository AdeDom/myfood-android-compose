package com.adedom.authentication.presentation.view_model

import com.adedom.authentication.domain.use_cases.FavoriteUseCase
import com.adedom.authentication.domain.use_cases.LoginUseCase
import com.adedom.core.utils.ApiServiceException
import com.adedom.core.utils.toBaseError
import com.adedom.ui_components.base.BaseViewModel
import com.adedom.user_profile.domain.use_cases.FetchUserProfileUseCase
import com.myfood.server.data.models.base.BaseError
import com.myfood.server.usecase.validate.ValidateEmailUseCase
import com.myfood.server.usecase.validate.ValidatePasswordUseCase
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
    private val fetchUserProfileUseCase: FetchUserProfileUseCase,
    private val favoriteUseCase: FavoriteUseCase,
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

                    try {
                        loginUseCase(uiState.email, uiState.password)
                        fetchUserProfileUseCase()
                        favoriteUseCase()
                        _nav.send(Unit)
                    } catch (exception: ApiServiceException) {
                        setState {
                            copy(
                                isLogin = true,
                                dialog = LoginUiState.Dialog.Error(exception.toBaseError()),
                            )
                        }
                    } catch (exception: Throwable) {
                        setState {
                            copy(
                                isLogin = true,
                                dialog = LoginUiState.Dialog.Error(exception.toBaseError()),
                            )
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