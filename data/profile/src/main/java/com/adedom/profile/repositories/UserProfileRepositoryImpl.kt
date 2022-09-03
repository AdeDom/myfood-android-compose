package com.adedom.profile.repositories

import com.adedom.myfood.data.models.response.UserProfileResponse
import com.adedom.profile.providers.local.UserProfileLocalDataSource
import com.adedom.profile.providers.remote.ProfileRemoteDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import myfood.database.UserProfileEntity

class UserProfileRepositoryImpl(
    private val userProfileLocalDataSource: UserProfileLocalDataSource,
    private val profileRemoteDataSource: ProfileRemoteDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : UserProfileRepository {

    override suspend fun callUserProfile(): UserProfileResponse? {
        return withContext(ioDispatcher) {
            val userProfileResponse = profileRemoteDataSource.callUserProfile()
            userProfileResponse.result
        }
    }

    override fun getUserProfileFlow(): Flow<UserProfileEntity?> {
        return userProfileLocalDataSource.getUserProfileFlow()
    }

    override suspend fun getUserProfile(): UserProfileEntity? {
        return withContext(ioDispatcher) {
            userProfileLocalDataSource.getUserProfile()
        }
    }

    override suspend fun getImageProfile(): String? {
        return withContext(ioDispatcher) {
            userProfileLocalDataSource.getImageProfile()
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