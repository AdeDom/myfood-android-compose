package com.adedom.main.domain.repositories

interface AuthLogoutRepository {

    suspend fun callLogout(): String?

    suspend fun setUnAuthRole()
}