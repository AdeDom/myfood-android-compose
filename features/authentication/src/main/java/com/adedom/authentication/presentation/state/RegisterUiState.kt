package com.adedom.authentication.presentation.state

sealed interface RegisterUiState {
    object Initial : RegisterUiState

    object Login : RegisterUiState

    data class RegisterForm(
        val name: String = "",
        val email: String = "",
        val mobileNo: String = "",
        val address: String = "",
        val password: String = "",
        val confirmPassword: String = "",
    )
}