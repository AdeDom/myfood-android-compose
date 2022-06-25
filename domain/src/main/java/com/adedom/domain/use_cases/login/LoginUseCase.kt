package com.adedom.domain.use_cases.login

import com.adedom.data.models.error.AppErrorCode
import com.adedom.data.models.error.BaseError
import com.adedom.data.models.request.login.LoginRequest
import com.adedom.data.repositories.auth.AuthLoginRepository
import com.adedom.data.utils.UseCaseException

class LoginUseCase(
    private val authLoginRepository: AuthLoginRepository,
) {

    suspend operator fun invoke(email: String?, password: String?) {
        return when {
            email.isNullOrBlank() -> {
                val code = AppErrorCode.EmailIsNullOrBlank.code
                val baseError = BaseError(code = code)
                throw UseCaseException(baseError)
            }
            email.length < 4 -> {
                val code = AppErrorCode.EmailLessThanFour.code
                val baseError = BaseError(code = code)
                throw UseCaseException(baseError)
            }
            password.isNullOrBlank() -> {
                val code = AppErrorCode.PasswordIsNullOrBlank.code
                val baseError = BaseError(code = code)
                throw UseCaseException(baseError)
            }
            password.length < 4 -> {
                val code = AppErrorCode.PasswordLessThanFour.code
                val baseError = BaseError(code = code)
                throw UseCaseException(baseError)
            }
            else -> {
                val loginRequest = LoginRequest(email, password)
                val code = AppErrorCode.TokenIsNull.code
                val baseError = BaseError(code = code)
                val (accessToken, refreshToken) = authLoginRepository.callLogin(loginRequest)
                    ?: throw UseCaseException(baseError)
                if (accessToken.isNullOrBlank() || refreshToken.isNullOrBlank()) {
                    throw UseCaseException(baseError)
                } else {
                    authLoginRepository.saveToken(accessToken, refreshToken)
                    authLoginRepository.saveAuthRole()
                }
            }
        }
    }

    fun isValidateEmail(email: String?): Boolean {
        return when {
            email.isNullOrBlank() -> false
            email.length < 4 -> false
            else -> true
        }
    }

    fun isValidatePassword(password: String?): Boolean {
        return when {
            password.isNullOrBlank() -> false
            password.length < 4 -> false
            else -> true
        }
    }
}