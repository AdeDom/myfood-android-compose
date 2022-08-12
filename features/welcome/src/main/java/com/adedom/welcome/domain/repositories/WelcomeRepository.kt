package com.adedom.welcome.domain.repositories

interface WelcomeRepository {

    suspend fun setGuestRole()
}