package com.adedom.core.data

//sealed class Resource<out T : Any> {
//    data class Success<out T : Any>(val data: T) : Resource<T>()
//    data class Error(val error: BaseError) : Resource<Nothing>()
//}
//
//sealed class Resource2<out T : Any> {
//    data class Success<out T : Any>(val data: T) : Resource2<T>()
//    data class Error(val error: BaseError) : Resource2<Nothing>()
//    data class RefreshTokenExpired(val error: BaseError) : Resource2<Nothing>()
//}