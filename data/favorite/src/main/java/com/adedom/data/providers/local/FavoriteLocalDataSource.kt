package com.adedom.data.providers.local

import kotlinx.coroutines.flow.Flow
import myfood.database.FavoriteEntity

interface FavoriteLocalDataSource {

    suspend fun getFavoriteList(): List<FavoriteEntity>

    suspend fun getFavoriteCountByFoodId(foodId: Long): Long?

    fun getFavoriteCountByFoodIdFlow(foodId: Long): Flow<Long?>

    suspend fun getIsFavoriteByFoodId(foodId: Long): Long?

    suspend fun insertFavorite(favorite: FavoriteEntity)

    suspend fun saveFavoriteAll(favoriteList: List<FavoriteEntity>)

    suspend fun updateBackupFavorite(favoriteId: String, updated: String)

    suspend fun deleteFavoriteAll()
}