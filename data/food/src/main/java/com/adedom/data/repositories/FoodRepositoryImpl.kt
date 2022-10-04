package com.adedom.data.repositories

import com.adedom.data.providers.local.FoodLocalDataSource
import com.adedom.data.providers.remote.FoodRemoteDataSource
import com.myfood.server.data.models.response.FoodDetailResponse
import com.myfood.server.data.models.web_sockets.FavoriteWebSocketsResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import myfood.database.FoodEntity

class FoodRepositoryImpl(
    private val foodLocalDataSource: FoodLocalDataSource,
    private val foodRemoteDataSource: FoodRemoteDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : FoodRepository {

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

    override suspend fun updateFavoriteByFoodId(favorite: FavoriteWebSocketsResponse) {
        return withContext(ioDispatcher) {
            foodLocalDataSource.updateFavoriteByFoodId(favorite)
        }
    }

    override suspend fun deleteFoodAll() {
        return withContext(ioDispatcher) {
            foodLocalDataSource.deleteFoodAll()
        }
    }

    override suspend fun callFoodDetail(foodId: Int): FoodDetailResponse? {
        return withContext(ioDispatcher) {
            val foodDetailResponse = foodRemoteDataSource.callFoodDetail(foodId)
            foodDetailResponse.result
        }
    }
}