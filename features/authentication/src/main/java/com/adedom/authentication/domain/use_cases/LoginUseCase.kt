package com.adedom.authentication.domain.use_cases

import com.adedom.authentication.domain.repositories.AuthLoginRepository
import com.myfood.server.data.models.request.LoginRequest

class LoginUseCase(
    private val authLoginRepository: AuthLoginRepository,
) {

    suspend operator fun invoke(email: String, password: String) {
        val loginRequest = LoginRequest(email, password)
        val tokenResponse = authLoginRepository.callLogin(loginRequest) ?: throw Throwable("Error")
        authLoginRepository.saveToken(tokenResponse.accessToken, tokenResponse.refreshToken)
        authLoginRepository.saveAuthRole()
    }
}