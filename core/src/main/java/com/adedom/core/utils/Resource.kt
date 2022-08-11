package com.adedom.core.utils

import com.adedom.myfood.data.models.base.BaseError

sealed class Resource<out T : Any> {
    data class Success<out T : Any>(val data: T) : Resource<T>()
    data class Error(val error: BaseError) : Resource<Nothing>()
}