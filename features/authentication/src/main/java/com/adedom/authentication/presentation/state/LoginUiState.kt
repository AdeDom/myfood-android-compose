package com.adedom.authentication.presentation.state

import com.adedom.myfood.data.models.base.BaseError

data class LoginUiState(
    val isErrorEmail: Boolean = false,
    val isErrorPassword: Boolean = false,
    val isLogin: Boolean = false,
    val isLoading: Boolean = false,
    val error: BaseError? = null,
    val email: String = "",
    val password: String = "",
)