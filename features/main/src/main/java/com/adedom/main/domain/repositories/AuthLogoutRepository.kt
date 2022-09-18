package com.adedom.main.domain.repositories

import com.adedom.core.data.Resource
import com.adedom.core.utils.AuthRole

interface AuthLogoutRepository {

    suspend fun callLogout(): Resource<Unit>

    suspend fun getAuthRole(): AuthRole

    suspend fun setUnAuthRole()
}