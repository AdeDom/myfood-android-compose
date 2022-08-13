package com.adedom.authentication.presentation.state

data class LoginUiState(
    val isErrorEmail: Boolean = false,
    val isErrorPassword: Boolean = false,
    val isLogin: Boolean = false,
    val isLoading: Boolean = false,
    val email: String = "",
    val password: String = "",
)