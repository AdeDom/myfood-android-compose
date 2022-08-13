package com.adedom.authentication.presentation.state

data class RegisterUiState(
    val name: String = "",
    val email: String = "",
    val mobileNo: String = "",
    val address: String = "",
    val password: String = "",
    val confirmPassword: String = "",
)