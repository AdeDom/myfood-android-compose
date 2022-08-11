package com.adedom.core.data.providers.data_store

import com.adedom.core.utils.AuthRole

interface AppDataStore {

    suspend fun setAccessToken(accessToken: String)

    suspend fun getAccessToken(): String?

    suspend fun setRefreshToken(refreshToken: String)

    suspend fun getRefreshToken(): String?

    suspend fun setAuthRole(authRole: AuthRole)

    suspend fun getAuthRole(): AuthRole
}