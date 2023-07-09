package com.adedom.welcome.data.repositories

import com.adedom.core.data.providers.datastore.AppDataStore
import com.adedom.core.utils.AuthRole
import com.adedom.welcome.domain.repositories.WelcomeRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class WelcomeRepositoryImpl(
    private val appDataStore: AppDataStore,
    private val ioDispatcher: CoroutineDispatcher
) : WelcomeRepository {

    override suspend fun setGuestRole() {
        withContext(ioDispatcher) {
            val authRole = AuthRole.Guest
            appDataStore.setAuthRole(authRole)
        }
    }
}