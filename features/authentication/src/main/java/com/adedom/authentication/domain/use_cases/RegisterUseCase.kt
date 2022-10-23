package com.adedom.authentication.domain.use_cases

import com.adedom.authentication.domain.repositories.AuthRepository
import com.myfood.server.data.models.request.RegisterRequest

class RegisterUseCase(
    private val authRepository: AuthRepository,
) {

    suspend operator fun invoke(
        email: String,
        password: String,
        name: String,
        mobileNo: String?,
        address: String?,
    ) {
        val registerRequest = RegisterRequest(
            email = email,
            password = password,
            name = name,
            mobileNo = mobileNo,
            address = address,
        )
        val tokenResponse = authRepository.callRegister(registerRequest) ?: throw Throwable("Error")
        authRepository.saveToken(tokenResponse.accessToken, tokenResponse.refreshToken)
        authRepository.saveAuthRole()
    }
}