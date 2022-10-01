package com.adedom.main.domain.use_cases

import com.adedom.main.domain.repositories.AuthLogoutRepository
import com.adedom.profile.repositories.UserProfileRepository

class LogoutUseCase(
    private val authLogoutRepository: AuthLogoutRepository,
    private val userProfileRepository: UserProfileRepository,
) {

    suspend operator fun invoke() {
        authLogoutRepository.setUnAuthRole()
        userProfileRepository.deleteUserProfile()
        authLogoutRepository.callLogout()
    }
}