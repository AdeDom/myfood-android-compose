package com.adedom.authentication.domain.use_cases

import com.adedom.authentication.domain.repositories.AuthRepository
import com.myfood.server.data.models.request.LoginRequest

class LoginUseCase(
    private val authRepository: AuthRepository,
) {

    suspend operator fun invoke(email: String, password: String) {
        val loginRequest = LoginRequest(email, password)
        val tokenResponse = authRepository.callLogin(loginRequest) ?: throw Throwable("Error")
        authRepository.saveToken(tokenResponse.accessToken, tokenResponse.refreshToken)
        authRepository.saveAuthRole()
    }
}