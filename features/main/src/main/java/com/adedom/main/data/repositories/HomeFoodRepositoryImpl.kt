package com.adedom.main.data.repositories

import com.adedom.main.data.providers.local.food.FoodLocalDataSource
import com.adedom.main.data.providers.remote.food.FoodRemoteDataSource
import com.adedom.main.domain.repositories.HomeFoodRepository
import com.myfood.server.data.models.response.FoodDetailResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import myfood.database.FoodEntity

class HomeFoodRepositoryImpl(
    private val foodLocalDataSource: FoodLocalDataSource,
    private val foodRemoteDataSource: FoodRemoteDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : HomeFoodRepository {

    override suspend fun callFoodListByCategoryId(categoryId: Int): List<FoodDetailResponse> {
        return withContext(ioDispatcher) {
            val foodListResponse = foodRemoteDataSource.callFoodListByCategoryId(categoryId)
            foodListResponse.result ?: emptyList()
        }
    }

    override fun getFoodListFlow(): Flow<List<FoodEntity>> {
        return foodLocalDataSource.getFoodListFlow().flowOn(ioDispatcher)
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