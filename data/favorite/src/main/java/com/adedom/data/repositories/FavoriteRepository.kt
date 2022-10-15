package com.adedom.data.repositories

import com.myfood.server.data.models.response.FavoriteResponse
import kotlinx.coroutines.flow.Flow
import myfood.database.FavoriteEntity

interface FavoriteRepository {

    suspend fun callFavoriteAll(): List<FavoriteResponse>?

    suspend fun getFavoriteList(): List<FavoriteEntity>

    suspend fun getFavoriteCountByFoodId(foodId: Long): Long?

    fun getFavoriteCountByFoodIdFlow(foodId: Long): Flow<Long?>

    suspend fun insertFavorite(favorite: FavoriteEntity)

    suspend fun saveFavoriteAll(favoriteList: List<FavoriteEntity>)

    suspend fun deleteFavoriteAll()
}