package com.adedom.main.data.repositories

import com.adedom.core.data.providers.data_store.AppDataStore
import com.adedom.core.utils.AuthRole
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

    override suspend fun callLogout() {
        return withContext(ioDispatcher) {
            authRemoteDataSource.callLogout()
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