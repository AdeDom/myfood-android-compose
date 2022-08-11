package com.adedom.core.utils

import com.adedom.myfood.data.models.base.BaseError
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun BaseError.toMessage(): String {
    return Json.encodeToString(this)
}