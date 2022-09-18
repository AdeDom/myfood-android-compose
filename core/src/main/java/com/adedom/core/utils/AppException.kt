package com.adedom.core.utils

import com.adedom.myfood.data.models.base.BaseError
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.IOException

class ApiServiceException(
    baseError: BaseError?
) : IOException(Json.encodeToString(baseError)) {

    fun toBaseError(): BaseError {
        return message?.let { msg ->
            Json.decodeFromString(msg)
        } ?: BaseError(message = message)
    }
}

class RefreshTokenExpiredException(
    baseError: BaseError?
) : IOException(Json.encodeToString(baseError)) {

    fun toBaseError(): BaseError {
        return message?.let { msg ->
            Json.decodeFromString(msg)
        } ?: BaseError(message = message)
    }
}