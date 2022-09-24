package com.adedom.authentication.domain.use_cases

import com.adedom.authentication.domain.repositories.AuthLoginRepository
import com.adedom.core.data.Resource
import com.adedom.core.data.Resource2
import com.adedom.profile.repositories.UserProfileRepository
import com.myfood.server.data.models.request.LoginRequest
import com.myfood.server.data.models.response.TokenResponse
import com.myfood.server.data.models.response.UserProfileResponse
import myfood.database.UserProfileEntity

class LoginUseCase(
    private val authLoginRepository: AuthLoginRepository,
    private val userProfileRepository: UserProfileRepository,
) {

    suspend operator fun invoke(email: String, password: String): Resource<TokenResponse> {
        val loginRequest = LoginRequest(email, password)
        val tokenResponseResult = authLoginRepository.callLogin(loginRequest)
        if (tokenResponseResult is Resource.Success) {
            val accessToken = tokenResponseResult.data.accessToken
            val refreshToken = tokenResponseResult.data.refreshToken
            authLoginRepository.saveToken(accessToken, refreshToken)
            authLoginRepository.saveAuthRole()

            val userProfileResult = userProfileRepository.callUserProfile()
            return when (userProfileResult) {
                is Resource2.Success -> {
                    val userProfileEntity =
                        mapUserProfileToUserProfileEntity(userProfileResult.data)
                    userProfileRepository.saveUserProfile(userProfileEntity)
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

    private fun mapUserProfileToUserProfileEntity(userProfile: UserProfileResponse?): UserProfileEntity {
        return UserProfileEntity(
            userProfile?.userId.orEmpty(),
            userProfile?.email.orEmpty(),
            userProfile?.name.orEmpty(),
            userProfile?.mobileNo,
            userProfile?.address,
            userProfile?.image,
            userProfile?.status.orEmpty(),
            userProfile?.created.orEmpty(),
            userProfile?.updated,
        )
    }
}