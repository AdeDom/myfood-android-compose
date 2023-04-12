package com.adedom.authentication.data.repositories

import com.adedom.authentication.data.providers.remote.auth.AuthRemoteDataSource
import com.adedom.authentication.domain.repositories.AuthRepository
import com.adedom.core.data.providers.data_store.AppDataStore
import com.adedom.core.utils.AuthRole
import com.myfood.server.data.models.request.LoginRequest
import com.myfood.server.data.models.request.RegisterRequest
import com.myfood.server.data.models.response.TokenResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class AuthRepositoryImpl(
    private val appDataStore: AppDataStore,
    private val authRemoteDataSource: AuthRemoteDataSource,
    private val ioDispatcher: CoroutineDispatcher
) : AuthRepository {

    override suspend fun callLogin(loginRequest: LoginRequest): TokenResponse? {
        return withContext(ioDispatcher) {
            val loginResponse = authRemoteDataSource.callLogin(loginRequest)
            loginResponse.result
        }
    }

    override suspend fun callRegister(registerRequest: RegisterRequest): TokenResponse? {
        return withContext(ioDispatcher) {
            val registerResponse = authRemoteDataSource.callRegister(registerRequest)
            registerResponse.result
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

    override suspend fun getAuthRole(): AuthRole {
        return withContext(ioDispatcher) {
            appDataStore.getAuthRole()
        }
    }
}