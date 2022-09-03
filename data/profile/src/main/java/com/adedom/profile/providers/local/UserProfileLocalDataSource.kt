package com.adedom.profile.providers.local

import kotlinx.coroutines.flow.Flow
import myfood.database.UserProfileEntity

interface UserProfileLocalDataSource {

    fun getUserProfileFlow(): Flow<UserProfileEntity?>

    suspend fun getUserProfile(): UserProfileEntity?

    suspend fun getImageProfile(): String?

    suspend fun saveUserProfile(userProfile: UserProfileEntity)

    suspend fun deleteUserProfile()
}