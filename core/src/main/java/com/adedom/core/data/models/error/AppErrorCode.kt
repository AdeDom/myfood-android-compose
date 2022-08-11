package com.adedom.core.data.models.error

sealed class AppErrorCode(val code: String) {
    object EmailIsNullOrBlank : AppErrorCode("APP-001")
    object EmailLessThanFour : AppErrorCode("APP-002")
    object PasswordIsNullOrBlank : AppErrorCode("APP-003")
    object PasswordLessThanFour : AppErrorCode("APP-004")
    object TokenIsNull : AppErrorCode("APP-005")
}