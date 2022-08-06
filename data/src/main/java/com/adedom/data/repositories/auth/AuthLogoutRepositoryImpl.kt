package com.adedom.data.repositories.auth

import com.adedom.data.providers.data_store.AppDataStore
import com.adedom.data.providers.remote.auth.AuthRemoteDataSource
import com.adedom.data.utils.AuthRole
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthLogoutRepositoryImpl(
    private val appDataStore: AppDataStore,
    private val authRemoteDataSource: AuthRemoteDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : AuthLogoutRepository {

    override suspend fun callLogout(): String? {
        return withContext(ioDispatcher) {
            val logoutResponse = authRemoteDataSource.callLogout()
            logoutResponse.result
        }
    }

    override suspend fun setUnAuthRole() {
        return withContext(ioDispatcher) {
            val authRole = AuthRole.UnAuth
            appDataStore.setAuthRole(authRole)
        }
    }
}