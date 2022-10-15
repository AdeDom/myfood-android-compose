package com.adedom.data.providers.local

import com.adedom.myfood.MyFoodDatabase
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToOneOrNull
import kotlinx.coroutines.flow.Flow
import myfood.database.FavoriteEntity
import myfood.database.FavoriteQueries

class FavoriteLocalDataSourceImpl(
    db: MyFoodDatabase,
) : FavoriteLocalDataSource {

    private val queries: FavoriteQueries = db.favoriteQueries

    override suspend fun getFavoriteList(): List<FavoriteEntity> {
        return queries.getFavoriteList().executeAsList()
    }

    override suspend fun getFavoriteCountByFoodId(foodId: Long): Long? {
        return queries.getFavoriteCountByFoodId(foodId).executeAsOneOrNull()
    }

    override fun getFavoriteCountByFoodIdFlow(foodId: Long): Flow<Long?> {
        return queries.getFavoriteCountByFoodId(foodId).asFlow().mapToOneOrNull()
    }

    override suspend fun insertFavorite(favorite: FavoriteEntity) {
        return queries.insertFavorite(
            favoriteId = favorite.favoriteId,
            userId = favorite.userId,
            foodId = favorite.foodId,
            isFavorite = favorite.isFavorite,
            isBackup = favorite.isBackup,
            created = favorite.created,
            updated = favorite.updated,
        )
    }

    override suspend fun saveFavoriteAll(favoriteList: List<FavoriteEntity>) {
        return favoriteList.forEach { favorite ->
            queries.insertFavorite(
                favoriteId = favorite.favoriteId,
                userId = favorite.userId,
                foodId = favorite.foodId,
                isFavorite = favorite.isFavorite,
                isBackup = favorite.isBackup,
                created = favorite.created,
                updated = favorite.updated,
            )
        }
    }

    override suspend fun deleteFavoriteAll() {
        return queries.deleteFavoriteAll()
    }
}