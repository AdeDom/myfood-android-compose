package com.adedom.domain.use_cases.user_profile

import com.adedom.data.repositories.profile.UserProfileRepository
import com.adedom.domain.models.user_profile.UserProfileModel
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
                    email = userProfileEntity?.email.orEmpty(),
                    name = userProfileEntity?.name.orEmpty(),
                    mobileNo = userProfileEntity?.mobileNo ?: "-",
                    address = userProfileEntity?.address ?: "-",
                    image = userProfileEntity?.image.orEmpty(),
                )
            }
    }
}