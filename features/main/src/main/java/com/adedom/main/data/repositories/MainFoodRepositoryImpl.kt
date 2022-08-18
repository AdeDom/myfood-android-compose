package com.adedom.main.data.repositories

import com.adedom.main.data.providers.local.food.FoodLocalDataSource
import com.adedom.main.domain.repositories.MainFoodRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import myfood.database.FoodEntity

class MainFoodRepositoryImpl(
    private val foodLocalDataSource: FoodLocalDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : MainFoodRepository {

    override fun getFoodListFlow(): Flow<List<FoodEntity>> {
        return foodLocalDataSource.getFoodListFlow()
    }

    override suspend fun getFoodList(): List<FoodEntity> {
        return withContext(ioDispatcher) {
            foodLocalDataSource.getFoodList()
        }
    }

    override suspend fun saveFoodAll(foodList: List<FoodEntity>) {
        return withContext(ioDispatcher) {
            foodLocalDataSource.saveFoodAll(foodList)
        }
    }

    override suspend fun deleteFoodAll() {
        return withContext(ioDispatcher) {
            foodLocalDataSource.deleteFoodAll()
        }
    }
}