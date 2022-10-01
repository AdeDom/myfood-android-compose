package com.adedom.food_detail.data.repositories

import com.adedom.food_detail.data.providers.remote.FoodRemoteDataSource
import com.adedom.food_detail.domain.repositories.FoodDetailRepository
import com.myfood.server.data.models.response.FoodDetailResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FoodDetailRepositoryImpl(
    private val foodRemoteDataSource: FoodRemoteDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : FoodDetailRepository {

    override suspend fun callFoodDetail(foodId: Int): FoodDetailResponse? {
        return withContext(ioDispatcher) {
            val foodDetailResponse = foodRemoteDataSource.callFoodDetail(foodId)
            foodDetailResponse.result
        }
    }
}