package com.adedom.main.domain.repositories

import kotlinx.coroutines.flow.Flow
import myfood.database.FoodEntity

interface MainFoodRepository {

    fun getFoodListFlow(): Flow<List<FoodEntity>>

    suspend fun getFoodList(): List<FoodEntity>

    suspend fun saveFoodAll(foodList: List<FoodEntity>)

    suspend fun deleteFoodAll()
}