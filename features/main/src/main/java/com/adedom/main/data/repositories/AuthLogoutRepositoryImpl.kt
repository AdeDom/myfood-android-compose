package com.adedom.main.data.repositories

import com.adedom.core.data.Resource
import com.adedom.core.data.providers.data_store.AppDataStore
import com.adedom.core.utils.ApiServiceException
import com.adedom.core.utils.AuthRole
import com.adedom.core.utils.RefreshTokenExpiredException
import com.adedom.main.data.providers.remote.auth.AuthRemoteDataSource
import com.adedom.main.domain.repositories.AuthLogoutRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthLogoutRepositoryImpl(
    private val appDataStore: AppDataStore,
    private val authRemoteDataSource: AuthRemoteDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : AuthLogoutRepository {

    override suspend fun callLogout(): Resource<Unit> {
        return withContext(ioDispatcher) {
            try {
                authRemoteDataSource.callLogout()
                Resource.Success(Unit)
            } catch (exception: ApiServiceException) {
                val baseError = exception.toBaseError()
                Resource.Error(baseError)
            } catch (exception: RefreshTokenExpiredException) {
                val baseError = exception.toBaseError()
                Resource.Error(baseError)
            }
        }
    }

    override suspend fun getAuthRole(): AuthRole {
        return withContext(ioDispatcher) {
            appDataStore.getAuthRole()
        }
    }

    override suspend fun setUnAuthRole() {
        return withContext(ioDispatcher) {
            val authRole = AuthRole.UnAuth
            appDataStore.setAuthRole(authRole)
        }
    }
}