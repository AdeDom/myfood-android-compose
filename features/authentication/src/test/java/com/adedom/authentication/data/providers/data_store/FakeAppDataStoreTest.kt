package com.adedom.authentication.data.providers.data_store

import com.adedom.core.data.providers.data_store.AppDataStore
import com.adedom.core.utils.AuthRole

class FakeAppDataStore : AppDataStore {
    private var accessToken: String? = null
    private var refreshToken: String? = null
    private var authRole: AuthRole = AuthRole.Unknown

    override suspend fun setAccessToken(accessToken: String) {
        this.accessToken = accessToken
    }

    override suspend fun getAccessToken(): String? {
        return accessToken
    }

    override suspend fun setRefreshToken(refreshToken: String) {
        this.refreshToken = refreshToken
    }

    override suspend fun getRefreshToken(): String? {
        return refreshToken
    }

    override suspend fun setAuthRole(authRole: AuthRole) {
        this.authRole = authRole
    }

    override suspend fun getAuthRole(): AuthRole {
        return authRole
    }
}