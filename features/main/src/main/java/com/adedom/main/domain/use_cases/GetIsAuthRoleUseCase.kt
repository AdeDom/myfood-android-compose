package com.adedom.main.domain.use_cases

import com.adedom.core.utils.AuthRole
import com.adedom.main.domain.repositories.AuthLogoutRepository

class GetIsAuthRoleUseCase(
    private val authLogoutRepository: AuthLogoutRepository,
) {

    suspend operator fun invoke(): Boolean {
        return authLogoutRepository.getAuthRole() == AuthRole.Auth
    }
}