package com.adedom.splash_screen.domain.repositories

import com.adedom.core.utils.AuthRole

interface SplashScreenRepository {

    suspend fun getAuthRole(): AuthRole
}