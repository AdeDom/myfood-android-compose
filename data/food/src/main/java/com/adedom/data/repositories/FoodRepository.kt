package com.adedom.data.repositories

import com.myfood.server.data.models.response.FoodDetailResponse
import com.myfood.server.data.models.web_sockets.FavoriteWebSocketsResponse
import myfood.database.FoodEntity

interface FoodRepository {

    suspend fun callFoodListByCategoryId(categoryId: Int): List<FoodDetailResponse>

    suspend fun getFoodList(): List<FoodEntity>

    suspend fun getFoodListByCategoryId(categoryId: Long): List<FoodEntity>

    suspend fun getFoodListBySearch(search: String): List<FoodEntity>

    suspend fun saveFoodAll(foodList: List<FoodEntity>)

    suspend fun updateFavoriteByFoodId(favorite: FavoriteWebSocketsResponse?)

    suspend fun deleteFoodAll()

    suspend fun callFoodDetail(foodId: Int): FoodDetailResponse?
}