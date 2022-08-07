package com.adedom.myfood.presentation.authentication.state

import com.adedom.myfood.data.models.base.BaseError

sealed interface LoginUiState {
    object Initial : LoginUiState

    data class ValidateEmail(
        val isError: Boolean,
        val isLogin: Boolean,
    ) : LoginUiState

    data class ValidatePassword(
        val isError: Boolean,
        val isLogin: Boolean,
    ) : LoginUiState

    object Loading : LoginUiState

    data class LoginError(
        val error: BaseError,
    ) : LoginUiState

    data class LoginForm(
        val email: String = "",
        val password: String = "",
    )
}