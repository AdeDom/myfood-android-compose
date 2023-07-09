package com.adedom.authentication.presentation.view_model

import androidx.lifecycle.viewModelScope
import com.adedom.authentication.domain.use_cases.FavoriteUseCase
import com.adedom.authentication.domain.use_cases.RegisterUseCase
import com.adedom.core.utils.ApiServiceException
import com.adedom.core.utils.toBaseError
import com.adedom.ui.components.base.BaseViewModel
import com.adedom.user_profile.domain.use_cases.FetchUserProfileUseCase
import com.myfood.server.data.models.base.BaseError
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

data class RegisterUiState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val dialog: Dialog? = null,
) {
    sealed interface Dialog {
        object Loading : Dialog
        data class Error(val error: BaseError) : Dialog
    }
}

sealed interface RegisterUiEvent {
    data class SetName(val value: String) : RegisterUiEvent
    data class SetEmail(val value: String) : RegisterUiEvent
    data class SetPassword(val value: String) : RegisterUiEvent
    data class SetConfirmPassword(val value: String) : RegisterUiEvent
    object Submit : RegisterUiEvent
    object HideErrorDialog : RegisterUiEvent
}

class RegisterViewModel(
    private val registerUseCase: RegisterUseCase,
    private val fetchUserProfileUseCase: FetchUserProfileUseCase,
    private val favoriteUseCase: FavoriteUseCase,
) : BaseViewModel<RegisterUiEvent, RegisterUiState>(RegisterUiState()) {

    private val _nav = Channel<Unit>()
    val nav: Flow<Unit> = _nav.receiveAsFlow()

    override fun onEvent(event: RegisterUiEvent) {
        viewModelScope.launch {
            when (event) {
                is RegisterUiEvent.SetName -> {
                    emit { copy(name = event.value) }
                }

                is RegisterUiEvent.SetEmail -> {
                    emit { copy(email = event.value) }
                }

                is RegisterUiEvent.SetPassword -> {
                    emit { copy(password = event.value) }
                }
                is RegisterUiEvent.SetConfirmPassword -> {
                    emit { copy(confirmPassword = event.value) }
                }
                RegisterUiEvent.Submit -> {
                    emit {
                        copy(dialog = RegisterUiState.Dialog.Loading)
                    }

                    try {
                        registerUseCase(
                            email = uiState.email,
                            password = uiState.password,
                            name = uiState.name,
                            mobileNo = null,
                            address = null,
                        )
                        fetchUserProfileUseCase()
                        favoriteUseCase()
                        _nav.send(Unit)
                    } catch (exception: ApiServiceException) {
                        emit {
                            copy(dialog = RegisterUiState.Dialog.Error(exception.toBaseError()))
                        }
                    } catch (exception: Throwable) {
                        emit {
                            copy(dialog = RegisterUiState.Dialog.Error(exception.toBaseError()))
                        }
                    }
                }
                RegisterUiEvent.HideErrorDialog -> {
                    emit { copy(dialog = null) }
                }
            }
        }
    }
}