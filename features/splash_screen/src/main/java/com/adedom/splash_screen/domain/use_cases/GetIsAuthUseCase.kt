package com.adedom.splash_screen.domain.use_cases

import com.adedom.core.utils.AuthRole
import com.adedom.splash_screen.domain.repositories.SplashScreenRepository

class GetIsAuthUseCase(
    private val splashScreenRepository: SplashScreenRepository,
) {

    suspend operator fun invoke(): Boolean {
        return when (splashScreenRepository.getAuthRole()) {
            AuthRole.Auth -> true
            AuthRole.UnAuth -> false
            AuthRole.Guest -> true
            AuthRole.Unknown -> false
        }
    }
}