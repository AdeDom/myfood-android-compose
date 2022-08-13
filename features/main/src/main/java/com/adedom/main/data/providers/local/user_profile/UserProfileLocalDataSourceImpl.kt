package com.adedom.main.data.providers.local.user_profile

import com.adedom.myfood.MyFoodDatabase
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToOneOrNull
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import myfood.database.MyFoodDatabaseQueries
import myfood.database.UserProfileEntity

class UserProfileLocalDataSourceImpl(
    db: MyFoodDatabase,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : UserProfileLocalDataSource {

    private val queries: MyFoodDatabaseQueries = db.myFoodDatabaseQueries

    override fun getUserProfileFlow(): Flow<UserProfileEntity?> {
        return queries.getUserProfile().asFlow().mapToOneOrNull(ioDispatcher)
    }

    override suspend fun getUserProfile(): UserProfileEntity? {
        return queries.getUserProfile().executeAsOneOrNull()
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