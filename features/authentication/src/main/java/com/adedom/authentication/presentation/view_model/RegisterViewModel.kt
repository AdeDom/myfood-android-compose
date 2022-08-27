package com.adedom.authentication.presentation.view_model

import androidx.lifecycle.viewModelScope
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

class RegisterViewModel : BaseViewModel<RegisterUiState, RegisterUiEvent>(RegisterUiState()) {

    fun dispatch(action: RegisterUiAction) {
        viewModelScope.launch {
            when (action) {
                is RegisterUiAction.SetName -> {
                    uiState = uiState.copy(name = action.value)
                }
                is RegisterUiAction.SetEmail -> {
                    uiState = uiState.copy(email = action.value)
                }
                is RegisterUiAction.SetMobileNo -> {
                    uiState = uiState.copy(mobileNo = action.value)
                }
                is RegisterUiAction.SetAddress -> {
                    uiState = uiState.copy(address = action.value)
                }
                is RegisterUiAction.SetPassword -> {
                    uiState = uiState.copy(password = action.value)
                }
                is RegisterUiAction.SetConfirmPassword -> {
                    uiState = uiState.copy(confirmPassword = action.value)
                }
                RegisterUiAction.Submit -> {
                    _uiEvent.emit(RegisterUiEvent.NavMain)
                }
                RegisterUiAction.NavLogin -> {
                    _uiEvent.emit(RegisterUiEvent.NavLogin)
                }
            }
        }
    }
}