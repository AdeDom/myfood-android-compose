package com.adedom.authentication.presentation.view_model

import com.adedom.ui_components.base.BaseViewModel
import kotlinx.coroutines.launch

data class RegisterUiState(
    val name: String = "",
    val email: String = "",
    val mobileNo: String = "",
    val address: String = "",
    val password: String = "",
    val confirmPassword: String = "",
)

sealed interface RegisterUiEvent {
    object NavLogin : RegisterUiEvent
    object NavMain : RegisterUiEvent
}

sealed interface RegisterUiAction {
    data class SetName(val value: String) : RegisterUiAction
    data class SetEmail(val value: String) : RegisterUiAction
    data class SetMobileNo(val value: String) : RegisterUiAction
    data class SetAddress(val value: String) : RegisterUiAction
    data class SetPassword(val value: String) : RegisterUiAction
    data class SetConfirmPassword(val value: String) : RegisterUiAction
    object Submit : RegisterUiAction
    object NavLogin : RegisterUiAction
}

class RegisterViewModel : BaseViewModel<RegisterUiState, RegisterUiEvent, RegisterUiAction>(
    RegisterUiState()
) {

    override fun dispatch(action: RegisterUiAction) {
        launch {
            when (action) {
                is RegisterUiAction.SetName -> {
                    setState { copy(name = action.value) }
                }
                is RegisterUiAction.SetEmail -> {
                    setState { copy(email = action.value) }
                }
                is RegisterUiAction.SetMobileNo -> {
                    setState { copy(mobileNo = action.value) }
                }
                is RegisterUiAction.SetAddress -> {
                    setState { copy(address = action.value) }
                }
                is RegisterUiAction.SetPassword -> {
                    setState { copy(password = action.value) }
                }
                is RegisterUiAction.SetConfirmPassword -> {
                    setState { copy(confirmPassword = action.value) }
                }
                RegisterUiAction.Submit -> {
                    setEvent(RegisterUiEvent.NavMain)
                }
                RegisterUiAction.NavLogin -> {
                    setEvent(RegisterUiEvent.NavLogin)
                }
            }
        }
    }
}