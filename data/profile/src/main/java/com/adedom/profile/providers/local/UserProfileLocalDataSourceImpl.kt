package com.adedom.profile.providers.local

import com.adedom.myfood.MyFoodDatabase
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToOneOrNull
import kotlinx.coroutines.flow.Flow
import myfood.database.UserProfileEntity
import myfood.database.UserProfileQueries

class UserProfileLocalDataSourceImpl(
    db: MyFoodDatabase,
) : UserProfileLocalDataSource {

    private val queries: UserProfileQueries = db.userProfileQueries

    override fun getUserProfileFlow(): Flow<UserProfileEntity?> {
        return queries.getUserProfile().asFlow().mapToOneOrNull()
    }

    override suspend fun getUserProfile(): UserProfileEntity? {
        return queries.getUserProfile().executeAsOneOrNull()
    }

    override suspend fun getImageProfile(): String? {
        return queries.getImageProfile().executeAsOneOrNull()?.image
    }

    override suspend fun getMyUserId(): String? {
        return queries.getUserId().executeAsOneOrNull()
    }

    override suspend fun saveUserProfile(userProfile: UserProfileEntity) {
        return queries.saveUserProfile(
            userId = userProfile.userId,
            email = userProfile.email,
            name = userProfile.name,
            mobileNo = userProfile.mobileNo,
            address = userProfile.address,
            image = userProfile.image,
            status = userProfile.status,
            created = userProfile.created,
            updated = userProfile.updated,
        )
    }

    override suspend fun deleteUserProfile() {
        return queries.deleteUserProfile()
    }
}