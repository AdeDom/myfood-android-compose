package com.adedom.main.domain.use_cases

import com.adedom.main.domain.repositories.AuthLogoutRepository

class SaveUnAuthRoleUseCase(
    private val authLogoutRepository: AuthLogoutRepository,
) {

    suspend operator fun invoke() {
        return authLogoutRepository.setUnAuthRole()
    }
}