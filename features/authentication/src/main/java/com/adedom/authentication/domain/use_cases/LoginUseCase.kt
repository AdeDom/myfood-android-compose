package com.adedom.authentication.domain.use_cases

import com.adedom.authentication.domain.repositories.AuthLoginRepository
import com.adedom.authentication.domain.repositories.UserProfileRepository
import com.adedom.core.data.models.error.AppErrorCode
import com.adedom.core.utils.ApiServiceException
import com.adedom.core.utils.Resource
import com.adedom.myfood.data.models.base.BaseError
import com.adedom.myfood.data.models.request.LoginRequest
import com.adedom.myfood.data.models.response.UserProfileResponse
import myfood.database.UserProfileEntity

class LoginUseCase(
    private val authLoginRepository: AuthLoginRepository,
    private val userProfileRepository: UserProfileRepository,
) {

    suspend operator fun invoke(email: String, password: String): Resource<Unit> {
        return try {
            val loginRequest = LoginRequest(email, password)
            val tokenResponse = authLoginRepository.callLogin(loginRequest)
            val accessToken = tokenResponse?.accessToken
            val refreshToken = tokenResponse?.refreshToken
            if (accessToken != null && refreshToken != null) {
                authLoginRepository.saveToken(accessToken, refreshToken)
                authLoginRepository.saveAuthRole()

                val userProfile = userProfileRepository.callUserProfile()
                val userProfileEntity = mapUserProfileToUserProfileEntity(userProfile)
                userProfileRepository.saveUserProfile(userProfileEntity)
                Resource.Success(Unit)
            } else {
                val code = AppErrorCode.TokenIsNull.code
                val baseError = BaseError(code = code)
                Resource.Error(baseError)
            }
        } catch (exception: ApiServiceException) {
            val baseError = exception.toBaseError()
            Resource.Error(baseError)
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