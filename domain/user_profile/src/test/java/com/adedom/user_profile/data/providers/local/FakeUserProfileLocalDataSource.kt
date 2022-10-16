package com.adedom.user_profile.data.providers.local

import com.adedom.profile.providers.local.UserProfileLocalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import myfood.database.UserProfileEntity

class FakeUserProfileLocalDataSource : UserProfileLocalDataSource {

    private var userProfileEntity: UserProfileEntity? = null

    override fun getUserProfileFlow(): Flow<UserProfileEntity?> {
        return flow {
            emit(userProfileEntity)
        }
    }

    override suspend fun getUserProfile(): UserProfileEntity? {
        return userProfileEntity
    }

    override suspend fun getImageProfile(): String? {
        return userProfileEntity?.image
    }

    override suspend fun getMyUserId(): String? {
        return userProfileEntity?.userId
    }

    override suspend fun saveUserProfile(userProfile: UserProfileEntity) {
        userProfileEntity = userProfile
    }

    override suspend fun deleteUserProfile() {
        userProfileEntity = null
    }
}