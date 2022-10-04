package com.adedom.data.providers.remote

import com.myfood.server.data.models.base.BaseResponse
import com.myfood.server.data.models.response.FoodDetailResponse

interface FoodRemoteDataSource {

    suspend fun callFoodListByCategoryId(categoryId: Int): BaseResponse<List<FoodDetailResponse>>

    suspend fun callFoodDetail(foodId: Int): BaseResponse<FoodDetailResponse>
}