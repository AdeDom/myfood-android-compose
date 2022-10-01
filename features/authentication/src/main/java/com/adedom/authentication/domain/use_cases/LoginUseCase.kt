package com.adedom.authentication.domain.use_cases

import com.adedom.authentication.domain.repositories.AuthLoginRepository
import com.adedom.core.data.Resource
import com.adedom.core.data.Resource2
import com.adedom.user_profile.domain.use_cases.FetchUserProfileUseCase
import com.myfood.server.data.models.request.LoginRequest
import com.myfood.server.data.models.response.TokenResponse

class LoginUseCase(
    private val authLoginRepository: AuthLoginRepository,
    private val fetchUserProfileUseCase: FetchUserProfileUseCase,
) {

    suspend operator fun invoke(email: String, password: String): Resource<TokenResponse> {
        val loginRequest = LoginRequest(email, password)
        val tokenResponseResult = authLoginRepository.callLogin(loginRequest)
        if (tokenResponseResult is Resource.Success) {
            val accessToken = tokenResponseResult.data.accessToken
            val refreshToken = tokenResponseResult.data.refreshToken
            authLoginRepository.saveToken(accessToken, refreshToken)
            authLoginRepository.saveAuthRole()

            val userProfileResult = fetchUserProfileUseCase()
            return when (userProfileResult) {
                is Resource2.Success -> {
                    Resource.Success(tokenResponseResult.data)
                }
                is Resource2.Error -> {
                    Resource.Error(userProfileResult.error)
                }
                is Resource2.RefreshTokenExpired -> {
                    Resource.Error(userProfileResult.error)
                }
            }
        } else {
            return tokenResponseResult
        }
    }
}