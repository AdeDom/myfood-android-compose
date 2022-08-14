package com.adedom.food_detail.data.providers.remote

import com.adedom.myfood.data.models.base.BaseResponse
import com.adedom.myfood.data.models.response.FoodDetailResponse

interface FoodRemoteDataSource {

    suspend fun callFoodDetail(foodId: Int): BaseResponse<FoodDetailResponse>
}