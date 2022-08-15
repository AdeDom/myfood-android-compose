package com.adedom.main.data.repositories

import com.adedom.main.data.providers.remote.food.FoodRemoteDataSource
import com.adedom.main.domain.repositories.HomeRepository
import com.adedom.myfood.data.models.response.FoodDetailResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HomeRepositoryImpl(
    private val foodRemoteDataSource: FoodRemoteDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : HomeRepository {

    override suspend fun callFoodListByCategoryId(categoryId: Int): List<FoodDetailResponse> {
        return withContext(ioDispatcher) {
            val foodListResponse = foodRemoteDataSource.callFoodListByCategoryId(categoryId)
            foodListResponse.result ?: emptyList()
        }
    }
}