package com.adedom.authentication.presentation.view_model

import androidx.lifecycle.viewModelScope
import com.adedom.authentication.domain.use_cases.FavoriteUseCase
import com.adedom.authentication.domain.use_cases.LoginUseCase
import com.adedom.core.utils.ApiServiceException
import com.adedom.core.utils.toBaseError
import com.adedom.ui.components.base.BaseViewModel
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

    override fun onEvent(event: LoginUiEvent) {
        viewModelScope.launch {
            when (event) {
                is LoginUiEvent.SetEmail -> {
                    val isValidateEmail = validateEmailUseCase(email = event.value)
                    val isValidatePassword = validatePasswordUseCase(uiState.password)
                    emit {
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
                    emit {
                        copy(
                            password = event.value,
                            isErrorPassword = !isValidatePassword,
                            isLogin = isValidateEmail && isValidatePassword,
                        )
                    }
                }
                LoginUiEvent.Submit -> {
                    emit {
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
                        emit {
                            copy(
                                isLogin = true,
                                dialog = LoginUiState.Dialog.Error(exception.toBaseError()),
                            )
                        }
                    } catch (exception: Throwable) {
                        emit {
                            copy(
                                isLogin = true,
                                dialog = LoginUiState.Dialog.Error(exception.toBaseError()),
                            )
                        }
                    }
                }
                LoginUiEvent.HideErrorDialog -> {
                    emit { copy(dialog = null) }
                }
            }
        }
    }
}