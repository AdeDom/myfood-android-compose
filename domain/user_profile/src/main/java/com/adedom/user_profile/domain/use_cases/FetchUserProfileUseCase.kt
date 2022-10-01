package com.adedom.user_profile.domain.use_cases

import com.adedom.profile.repositories.UserProfileRepository
import com.myfood.server.data.models.response.UserProfileResponse
import myfood.database.UserProfileEntity

class FetchUserProfileUseCase(
    private val userProfileRepository: UserProfileRepository,
) {

    suspend operator fun invoke() {
        val userProfileResponse = userProfileRepository.callUserProfile()
        val userProfileEntity = mapUserProfileToUserProfileEntity(userProfileResponse)
        userProfileRepository.deleteUserProfile()
        userProfileRepository.saveUserProfile(userProfileEntity)
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