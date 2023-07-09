package com.adedom.core.data.providers.datastore

import com.adedom.core.utils.AuthRole

interface AppDataStore {

    val accessTokenKey: String get() = "accessTokenKey"
    val refreshTokenKey: String get() = "refreshTokenKey"
    val authRoleKey: String get() = "authRoleKey"

    suspend fun setAccessToken(accessToken: String)

    suspend fun getAccessToken(): String?

    suspend fun setRefreshToken(refreshToken: String)

    suspend fun getRefreshToken(): String?

    suspend fun setAuthRole(authRole: AuthRole)

    suspend fun getAuthRole(): AuthRole

    companion object {
        const val fileName: String = "AppDataStoreFile"
    }
}
