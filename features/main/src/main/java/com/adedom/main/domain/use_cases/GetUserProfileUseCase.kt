package com.adedom.main.domain.use_cases

import com.adedom.main.domain.models.UserProfileModel
import com.adedom.main.domain.repositories.UserProfileRepository
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