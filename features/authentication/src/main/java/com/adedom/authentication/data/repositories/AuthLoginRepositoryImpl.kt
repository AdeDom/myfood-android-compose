package com.adedom.authentication.data.repositories

import com.adedom.authentication.data.providers.remote.auth.AuthRemoteDataSource
import com.adedom.authentication.domain.repositories.AuthLoginRepository
import com.adedom.core.data.Resource
import com.adedom.core.data.models.error.AppErrorCode
import com.adedom.core.data.providers.data_store.AppDataStore
import com.adedom.core.utils.ApiServiceException
import com.adedom.core.utils.AuthRole
import com.myfood.server.data.models.base.BaseError
import com.myfood.server.data.models.request.LoginRequest
import com.myfood.server.data.models.response.TokenResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthLoginRepositoryImpl(
    private val appDataStore: AppDataStore,
    private val authRemoteDataSource: AuthRemoteDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : AuthLoginRepository {

    override suspend fun callLogin(loginRequest: LoginRequest): Resource<TokenResponse> {
        return withContext(ioDispatcher) {
            try {
                val loginResponse = authRemoteDataSource.callLogin(loginRequest)
                val result = loginResponse.result
                if (result != null) {
                    Resource.Success(result)
                } else {
                    val baseError = BaseError(code = AppErrorCode.DataIsNull.code)
                    Resource.Error(baseError)
                }
            } catch (exception: ApiServiceException) {
                val baseError = exception.toBaseError()
                Resource.Error(baseError)
            }
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