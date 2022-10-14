package com.adedom.authentication.domain.use_cases

import com.adedom.authentication.domain.repositories.AuthLoginRepository
import com.adedom.user_profile.domain.use_cases.FetchUserProfileUseCase
import com.myfood.server.data.models.request.LoginRequest

class LoginUseCase(
    private val authLoginRepository: AuthLoginRepository,
    private val fetchUserProfileUseCase: FetchUserProfileUseCase,
    private val favoriteUseCase: FavoriteUseCase,
) {

    suspend operator fun invoke(email: String, password: String) {
        val loginRequest = LoginRequest(email, password)
        val tokenResponse = authLoginRepository.callLogin(loginRequest) ?: throw Throwable("Error")
        authLoginRepository.saveToken(tokenResponse.accessToken, tokenResponse.refreshToken)
        authLoginRepository.saveAuthRole()

        fetchUserProfileUseCase()
        favoriteUseCase()
    }
}