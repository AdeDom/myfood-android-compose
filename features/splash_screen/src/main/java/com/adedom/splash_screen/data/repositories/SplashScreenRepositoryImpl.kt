package com.adedom.splash_screen.data.repositories

import com.adedom.core.data.providers.data_store.AppDataStore
import com.adedom.core.utils.AuthRole
import com.adedom.splash_screen.domain.repositories.SplashScreenRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class SplashScreenRepositoryImpl(
    private val appDataStore: AppDataStore,
    private val ioDispatcher: CoroutineDispatcher
) : SplashScreenRepository {

    override suspend fun getAuthRole(): AuthRole {
        return withContext(ioDispatcher) {
            appDataStore.getAuthRole()
        }
    }
}