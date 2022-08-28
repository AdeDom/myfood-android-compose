package com.adedom.main.domain.repositories

import com.adedom.myfood.data.models.response.FoodDetailResponse
import kotlinx.coroutines.flow.Flow
import myfood.database.FoodEntity

interface HomeFoodRepository {

    suspend fun callFoodListByCategoryId(categoryId: Int): List<FoodDetailResponse>

    fun getFoodListFlow(): Flow<List<FoodEntity>>

    suspend fun getFoodList(): List<FoodEntity>

    suspend fun getFoodListByCategoryId(categoryId: Long): List<FoodEntity>

    suspend fun saveFoodAll(foodList: List<FoodEntity>)

    suspend fun deleteFoodAll()
}