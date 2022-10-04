package com.adedom.data.providers.local

import com.myfood.server.data.models.web_sockets.FavoriteWebSocketsResponse
import kotlinx.coroutines.flow.Flow
import myfood.database.FoodEntity

interface FoodLocalDataSource {

    fun getFoodListFlow(): Flow<List<FoodEntity>>

    suspend fun getFoodList(): List<FoodEntity>

    suspend fun getFoodListByCategoryId(categoryId: Long): List<FoodEntity>

    suspend fun getFoodListBySearch(search: String): List<FoodEntity>

    suspend fun saveFoodAll(foodList: List<FoodEntity>)

    suspend fun updateFavoriteByFoodId(favorite: FavoriteWebSocketsResponse)

    suspend fun deleteFoodAll()
}