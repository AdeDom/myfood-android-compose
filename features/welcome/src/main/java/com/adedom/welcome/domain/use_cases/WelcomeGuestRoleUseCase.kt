package com.adedom.welcome.domain.use_cases

import com.adedom.welcome.domain.repositories.WelcomeRepository

class WelcomeGuestRoleUseCase(
    private val welcomeRepository: WelcomeRepository,
) {

    suspend operator fun invoke() {
        return welcomeRepository.setGuestRole()
    }
}