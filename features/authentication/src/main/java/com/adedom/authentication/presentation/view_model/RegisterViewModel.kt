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
    data class SetName(val value: String) : RegisterUiEvent
    data class SetEmail(val value: String) : RegisterUiEvent
    data class SetMobileNo(val value: String) : RegisterUiEvent
    data class SetAddress(val value: String) : RegisterUiEvent
    data class SetPassword(val value: String) : RegisterUiEvent
    data class SetConfirmPassword(val value: String) : RegisterUiEvent
}

class RegisterViewModel : BaseViewModel<RegisterUiEvent, RegisterUiState>(RegisterUiState()) {

    override fun dispatch(event: RegisterUiEvent) {
        launch {
            when (event) {
                is RegisterUiEvent.SetName -> {
                    setState { copy(name = event.value) }
                }
                is RegisterUiEvent.SetEmail -> {
                    setState { copy(email = event.value) }
                }
                is RegisterUiEvent.SetMobileNo -> {
                    setState { copy(mobileNo = event.value) }
                }
                is RegisterUiEvent.SetAddress -> {
                    setState { copy(address = event.value) }
                }
                is RegisterUiEvent.SetPassword -> {
                    setState { copy(password = event.value) }
                }
                is RegisterUiEvent.SetConfirmPassword -> {
                    setState { copy(confirmPassword = event.value) }
                }
            }
        }
    }
}