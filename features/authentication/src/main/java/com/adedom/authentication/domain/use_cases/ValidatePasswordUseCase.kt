package com.adedom.authentication.domain.use_cases

class ValidatePasswordUseCase {

    operator fun invoke(password: String?): Boolean {
        return when {
            password.isNullOrBlank() -> false
            password.length < 4 -> false
            else -> true
        }
    }
}