package com.adedom.myfood.data.providers.data_store

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.adedom.data.providers.data_store.AppDataStore
import com.adedom.data.utils.AuthRole
import kotlinx.coroutines.flow.first

class AppDataStoreImpl(
    private val dataStoreFile: DataStore<Preferences>,
) : AppDataStore {

    private val accessToken = stringPreferencesKey("accessToken")
    private val refreshToken = stringPreferencesKey("refreshToken")
    private val authRole = intPreferencesKey("authRole")

    override suspend fun setAccessToken(accessToken: String) {
        dataStoreFile.edit { preferences ->
            preferences[this.accessToken] = accessToken
        }
    }

    override suspend fun getAccessToken(): String? {
        return dataStoreFile.data.first()[accessToken]
    }

    override suspend fun setRefreshToken(refreshToken: String) {
        dataStoreFile.edit { preferences ->
            preferences[this.refreshToken] = refreshToken
        }
    }

    override suspend fun getRefreshToken(): String? {
        return dataStoreFile.data.first()[refreshToken]
    }

    override suspend fun setAuthRole(authRole: AuthRole) {
        dataStoreFile.edit { preferences ->
            preferences[this.authRole] = authRole.value
        }
    }

    override suspend fun getAuthRole(): AuthRole {
        val authRole = dataStoreFile.data.first()[authRole]
        return when (authRole) {
            AuthRole.Auth.value -> AuthRole.Auth
            AuthRole.UnAuth.value -> AuthRole.UnAuth
            AuthRole.Guest.value -> AuthRole.Guest
            else -> AuthRole.Unknown
        }
    }
}