package com.adedom.data.repositories.welcome

import com.adedom.core.data.providers.data_store.AppDataStore
import com.adedom.core.utils.AuthRole
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WelcomeRepositoryImpl(
    private val appDataStore: AppDataStore,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : WelcomeRepository {

    override suspend fun setGuestRole() {
        withContext(ioDispatcher) {
            val authRole = AuthRole.Guest
            appDataStore.setAuthRole(authRole)
        }
    }
}