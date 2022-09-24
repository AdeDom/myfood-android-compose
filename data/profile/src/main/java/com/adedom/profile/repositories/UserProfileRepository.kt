package com.adedom.profile.repositories

import com.adedom.core.data.Resource2
import com.myfood.server.data.models.response.UserProfileResponse
import kotlinx.coroutines.flow.Flow
import myfood.database.UserProfileEntity

interface UserProfileRepository {

    suspend fun callUserProfile(): Resource2<UserProfileResponse>

    fun getUserProfileFlow(): Flow<UserProfileEntity?>

    suspend fun getUserProfile(): UserProfileEntity?

    suspend fun getImageProfile(): String?

    suspend fun saveUserProfile(userProfile: UserProfileEntity)

    suspend fun deleteUserProfile()
}