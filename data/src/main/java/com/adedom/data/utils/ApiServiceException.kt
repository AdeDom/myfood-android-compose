package com.adedom.data.utils

import com.adedom.myfood.data.models.base.BaseError
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.IOException

class ApiServiceException(baseError: BaseError?) : IOException(baseError?.toMessage()) {

    fun toBaseError(): BaseError {
        return message?.let { msg ->
            Json.decodeFromString(msg)
        } ?: BaseError(message = message)
    }
}