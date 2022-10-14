package com.adedom.data.providers.local

import com.adedom.myfood.MyFoodDatabase
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

    override suspend fun saveFavoriteAll(favoriteList: List<FavoriteEntity>) {
        return favoriteList.forEach { favorite ->
            queries.saveFavoriteAll(
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