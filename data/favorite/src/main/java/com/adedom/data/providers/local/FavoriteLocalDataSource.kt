package com.adedom.data.providers.local

import kotlinx.coroutines.flow.Flow
import myfood.database.FavoriteEntity

interface FavoriteLocalDataSource {

    suspend fun getFavoriteList(): List<FavoriteEntity>

    suspend fun getFavoriteByFoodId(foodId: Long): FavoriteEntity?

    fun getIsFavoriteByFoodIdFlow(foodId: Long): Flow<Long?>

    suspend fun insertOrReplaceFavorite(favorite: FavoriteEntity)

    suspend fun insertOrReplaceFavoriteAll(favoriteList: List<FavoriteEntity>)

    suspend fun updateBackupFavorite(favoriteId: String, updated: String)

    suspend fun deleteFavoriteAll()
}