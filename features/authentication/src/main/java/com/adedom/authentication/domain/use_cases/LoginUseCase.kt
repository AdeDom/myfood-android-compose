package com.adedom.authentication.domain.use_cases

import com.adedom.authentication.domain.repositories.AuthLoginRepository
import com.adedom.user_profile.domain.use_cases.FetchUserProfileUseCase
import com.myfood.server.data.models.request.LoginRequest
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class LoginUseCase(
    private val authLoginRepository: AuthLoginRepository,
    private val fetchUserProfileUseCase: FetchUserProfileUseCase,
    private val favoriteUseCase: FavoriteUseCase,
) {

    suspend operator fun invoke(email: String, password: String) {
        return coroutineScope {
            val loginRequest = LoginRequest(email, password)
            val tokenResponse = authLoginRepository.callLogin(loginRequest)
                ?: throw Throwable("Error")
            authLoginRepository.saveToken(tokenResponse.accessToken, tokenResponse.refreshToken)
            authLoginRepository.saveAuthRole()

            val userProfileAsync = async { fetchUserProfileUseCase() }
            val favoriteAsync = async { favoriteUseCase() }

            userProfileAsync.await()
            favoriteAsync.await()
        }
    }
}