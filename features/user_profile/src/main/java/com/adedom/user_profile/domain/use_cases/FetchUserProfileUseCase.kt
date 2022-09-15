package com.adedom.user_profile.domain.use_cases

import com.adedom.core.data.Resource2
import com.adedom.core.utils.ApiServiceException
import com.adedom.core.utils.RefreshTokenExpiredException
import com.adedom.myfood.data.models.response.UserProfileResponse
import com.adedom.profile.repositories.UserProfileRepository
import myfood.database.UserProfileEntity

class FetchUserProfileUseCase(
    private val userProfileRepository: UserProfileRepository,
) {

    suspend operator fun invoke(): Resource2<Unit> {
        return try {
            val userProfile = userProfileRepository.callUserProfile()
            val userProfileEntity = mapUserProfileToUserProfileEntity(userProfile)
            userProfileRepository.deleteUserProfile()
            userProfileRepository.saveUserProfile(userProfileEntity)
            Resource2.Success(Unit)
        } catch (exception: ApiServiceException) {
            val baseError = exception.toBaseError()
            Resource2.Error(baseError)
        } catch (exception: RefreshTokenExpiredException) {
            val baseError = exception.toBaseError()
            Resource2.RefreshTokenExpired(baseError)
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