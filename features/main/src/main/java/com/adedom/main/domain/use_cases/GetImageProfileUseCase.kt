package com.adedom.main.domain.use_cases

import com.adedom.profile.repositories.UserProfileRepository

class GetImageProfileUseCase(
    private val userProfileRepository: UserProfileRepository,
) {

    suspend operator fun invoke(): String? {
        return userProfileRepository.getImageProfile()
    }
}