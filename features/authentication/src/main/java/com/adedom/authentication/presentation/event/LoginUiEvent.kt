package com.adedom.authentication.presentation.event

import com.adedom.myfood.data.models.base.BaseError

sealed interface LoginUiEvent {

    object Register : LoginUiEvent

    object LoginSuccess : LoginUiEvent

    data class LoginError(
        val error: BaseError,
    ) : LoginUiEvent
}