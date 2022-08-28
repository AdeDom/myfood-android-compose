package com.adedom.main.domain.repositories

import kotlinx.coroutines.flow.Flow
import myfood.database.UserProfileEntity

interface UserProfileRepository {

    fun getUserProfileFlow(): Flow<UserProfileEntity?>

    suspend fun getUserProfile(): UserProfileEntity?

    suspend fun getImageProfile(): String?

    suspend fun saveUserProfile(userProfile: UserProfileEntity)

    suspend fun deleteUserProfile()
}