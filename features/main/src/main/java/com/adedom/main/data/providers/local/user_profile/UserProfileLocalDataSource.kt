package com.adedom.main.data.providers.local.user_profile

import kotlinx.coroutines.flow.Flow
import myfood.database.UserProfileEntity

interface UserProfileLocalDataSource {

    fun getUserProfileFlow(): Flow<UserProfileEntity?>

    suspend fun getUserProfile(): UserProfileEntity?

    suspend fun saveUserProfile(userProfile: UserProfileEntity)

    suspend fun deleteUserProfile()
}