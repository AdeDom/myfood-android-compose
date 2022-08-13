package com.adedom.main.domain.repositories

import com.adedom.myfood.data.models.response.UserProfileResponse
import kotlinx.coroutines.flow.Flow
import myfood.database.UserProfileEntity

interface UserProfileRepository {

    suspend fun callUserProfile(): UserProfileResponse?

    fun getUserProfileFlow(): Flow<UserProfileEntity?>

    suspend fun getUserProfile(): UserProfileEntity?

    suspend fun saveUserProfile(userProfile: UserProfileEntity)

    suspend fun deleteUserProfile()
}