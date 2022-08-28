package com.adedom.user_profile.domain.use_cases

import com.adedom.core.utils.ApiServiceException
import com.adedom.core.utils.Resource
import com.adedom.myfood.data.models.response.UserProfileResponse
import com.adedom.user_profile.domain.repositories.UserProfileRepository
import myfood.database.UserProfileEntity

class FetchUserProfileUseCase(
    private val userProfileRepository: UserProfileRepository,
) {

    suspend operator fun invoke(): Resource<Unit> {
        return try {
            val userProfile = userProfileRepository.callUserProfile()
            val userProfileEntity = mapUserProfileToUserProfileEntity(userProfile)
            userProfileRepository.deleteUserProfile()
            userProfileRepository.saveUserProfile(userProfileEntity)
            Resource.Success(Unit)
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