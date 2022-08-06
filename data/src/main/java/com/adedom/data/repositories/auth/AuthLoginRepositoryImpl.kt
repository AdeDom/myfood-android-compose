package com.adedom.data.repositories.auth

import com.adedom.data.providers.data_store.AppDataStore
import com.adedom.data.providers.remote.auth.AuthRemoteDataSource
import com.adedom.data.utils.AuthRole
import com.adedom.myfood.data.models.request.LoginRequest
import com.adedom.myfood.data.models.response.TokenResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthLoginRepositoryImpl(
    private val appDataStore: AppDataStore,
    private val authRemoteDataSource: AuthRemoteDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : AuthLoginRepository {

    override suspend fun callLogin(loginRequest: LoginRequest): TokenResponse? {
        return withContext(ioDispatcher) {
            val loginResponse = authRemoteDataSource.callLogin(loginRequest)
            loginResponse.result
        }
    }

    override suspend fun saveToken(accessToken: String, refreshToken: String) {
        return withContext(ioDispatcher) {
            appDataStore.setAccessToken(accessToken)
            appDataStore.setRefreshToken(refreshToken)
        }
    }

    override suspend fun saveAuthRole() {
        return withContext(ioDispatcher) {
            val authRole = AuthRole.Auth
            appDataStore.setAuthRole(authRole)
        }
    }
}