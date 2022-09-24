package com.adedom.core.data.providers.remote

import com.myfood.server.data.models.base.BaseError
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

@Serializable
data class ApiServiceResponse(
    var version: String = "1.0",
    var status: String = "error",
    var error: BaseError? = null,
)

fun String.decodeApiServiceResponseFromString(): ApiServiceResponse {
    return Json {
        ignoreUnknownKeys = true
    }.decodeFromString(this)
}