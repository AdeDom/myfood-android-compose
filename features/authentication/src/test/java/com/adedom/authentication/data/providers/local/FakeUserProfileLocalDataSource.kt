package com.adedom.authentication.data.providers.local

import com.adedom.authentication.data.providers.local.user_profile.UserProfileLocalDataSource
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

    override suspend fun saveUserProfile(userProfile: UserProfileEntity) {
        userProfileEntity = userProfile
    }

    override suspend fun deleteUserProfile() {
        userProfileEntity = null
    }
}