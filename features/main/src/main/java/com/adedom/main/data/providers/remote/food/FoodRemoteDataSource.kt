package com.adedom.main.data.providers.remote.food

import com.myfood.server.data.models.base.BaseResponse
import com.myfood.server.data.models.response.FoodDetailResponse

interface FoodRemoteDataSource {

    suspend fun callFoodListByCategoryId(categoryId: Int): BaseResponse<List<FoodDetailResponse>>
}