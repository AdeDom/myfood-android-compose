package com.adedom.user_profile.domain.use_cases

import com.adedom.profile.repositories.UserProfileRepository
import com.adedom.user_profile.domain.models.UserProfileModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetUserProfileUseCase(
    private val userProfileRepository: UserProfileRepository,
) {

    operator fun invoke(): Flow<UserProfileModel> {
        return userProfileRepository.getUserProfileFlow()
            .map { userProfileEntity ->
                UserProfileModel(
                    userId = userProfileEntity?.userId.orEmpty(),
                    name = userProfileEntity?.name.orEmpty(),
                    email = userProfileEntity?.email.orEmpty(),
                    image = userProfileEntity?.image.orEmpty(),
                    address = userProfileEntity?.address.orEmpty(),
                    mobileNo = userProfileEntity?.mobileNo.orEmpty(),
                )
            }
    }
}