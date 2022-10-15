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

    override suspend fun getFavoriteByFoodId(foodId: Long): FavoriteEntity? {
        return queries.getFavoriteByFoodId(foodId).executeAsOneOrNull()
    }

    override fun getIsFavoriteByFoodIdFlow(foodId: Long): Flow<Long?> {
        return queries.getIsFavoriteByFoodId(foodId).asFlow().mapToOneOrNull()
    }

    override suspend fun insertOrReplaceFavorite(favorite: FavoriteEntity) {
        return queries.insertOrReplaceFavorite(
            favoriteId = favorite.favoriteId,
            userId = favorite.userId,
            foodId = favorite.foodId,
            isFavorite = favorite.isFavorite,
            isBackup = favorite.isBackup,
            created = favorite.created,
            updated = favorite.updated,
        )
    }

    override suspend fun insertOrReplaceFavoriteAll(favoriteList: List<FavoriteEntity>) {
        return favoriteList.forEach { favorite ->
            queries.insertOrReplaceFavorite(
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

    override suspend fun updateBackupFavorite(favoriteId: String, updated: String) {
        return queries.updateBackupFavorite(updated, favoriteId)
    }

    override suspend fun deleteFavoriteAll() {
        return queries.deleteFavoriteAll()
    }
}