package com.adedom.main.domain.repositories

import com.adedom.core.utils.AuthRole

interface AuthLogoutRepository {

    suspend fun callLogout(): String?

    suspend fun getAuthRole(): AuthRole

    suspend fun setUnAuthRole()
}