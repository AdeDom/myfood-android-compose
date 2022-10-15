package com.adedom.data.repositories

import com.myfood.server.data.models.response.FavoriteResponse
import kotlinx.coroutines.flow.Flow
import myfood.database.FavoriteEntity

interface FavoriteRepository {

    suspend fun callFavoriteAll(): List<FavoriteResponse>?

    suspend fun getFavoriteByFoodId(foodId: Long): FavoriteEntity?

    fun getIsFavoriteByFoodIdFlow(foodId: Long): Flow<Long?>

    suspend fun insertOrReplaceFavorite(favorite: FavoriteEntity)

    suspend fun insertOrReplaceFavoriteAll(favoriteList: List<FavoriteEntity>)

    suspend fun updateBackupFavorite(favoriteId: String, updated: String)

    suspend fun deleteFavoriteAll()
}