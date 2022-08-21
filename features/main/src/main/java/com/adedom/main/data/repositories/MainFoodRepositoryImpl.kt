package com.adedom.main.data.repositories

import com.adedom.main.data.providers.local.food.FoodLocalDataSource
import com.adedom.main.data.providers.remote.food.FoodRemoteDataSource
import com.adedom.main.domain.repositories.MainFoodRepository
import com.adedom.myfood.data.models.response.FoodDetailResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import myfood.database.FoodEntity

class MainFoodRepositoryImpl(
    private val foodLocalDataSource: FoodLocalDataSource,
    private val foodRemoteDataSource: FoodRemoteDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : MainFoodRepository {

    override suspend fun callFoodListByCategoryId(categoryId: Int): List<FoodDetailResponse> {
        return withContext(ioDispatcher) {
            val foodListResponse = foodRemoteDataSource.callFoodListByCategoryId(categoryId)
            foodListResponse.result ?: emptyList()
        }
    }

    override fun getFoodListFlow(): Flow<List<FoodEntity>> {
        return foodLocalDataSource.getFoodListFlow()
    }

    override suspend fun getFoodList(): List<FoodEntity> {
        return withContext(ioDispatcher) {
            foodLocalDataSource.getFoodList()
        }
    }

    override suspend fun getFoodListByCategoryId(categoryId: Long): List<FoodEntity> {
        return withContext(ioDispatcher) {
            foodLocalDataSource.getFoodListByCategoryId(categoryId)
        }
    }

    override suspend fun getFoodListBySearch(search: String): List<FoodEntity> {
        return withContext(ioDispatcher) {
            foodLocalDataSource.getFoodListBySearch(search)
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