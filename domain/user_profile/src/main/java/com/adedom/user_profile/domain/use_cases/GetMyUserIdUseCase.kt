package com.adedom.user_profile.domain.use_cases

import com.adedom.profile.repositories.UserProfileRepository

class GetMyUserIdUseCase(
    private val userProfileRepository: UserProfileRepository,
) {

    suspend operator fun invoke(): String? {
        return userProfileRepository.getMyUserId()
    }
}