package com.adedom.main.data.repositories

import com.adedom.main.data.providers.local.user_profile.UserProfileLocalDataSource
import com.adedom.main.domain.repositories.UserProfileRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import myfood.database.UserProfileEntity

class UserProfileRepositoryImpl(
    private val userProfileLocalDataSource: UserProfileLocalDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : UserProfileRepository {

    override fun getUserProfileFlow(): Flow<UserProfileEntity?> {
        return userProfileLocalDataSource.getUserProfileFlow()
    }

    override suspend fun getUserProfile(): UserProfileEntity? {
        return withContext(ioDispatcher) {
            userProfileLocalDataSource.getUserProfile()
        }
    }

    override suspend fun saveUserProfile(userProfile: UserProfileEntity) {
        return withContext(ioDispatcher) {
            userProfileLocalDataSource.saveUserProfile(userProfile)
        }
    }

    override suspend fun deleteUserProfile() {
        return withContext(ioDispatcher) {
            userProfileLocalDataSource.deleteUserProfile()
        }
    }
}