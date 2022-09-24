package com.adedom.food_detail.data.providers.remote

import com.myfood.server.data.models.base.BaseResponse
import com.myfood.server.data.models.response.FoodDetailResponse

interface FoodRemoteDataSource {

    suspend fun callFoodDetail(foodId: Int): BaseResponse<FoodDetailResponse>
}