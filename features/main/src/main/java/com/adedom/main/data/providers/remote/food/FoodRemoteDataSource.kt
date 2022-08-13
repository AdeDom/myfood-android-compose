package com.adedom.main.data.providers.remote.food

import com.adedom.myfood.data.models.base.BaseResponse
import com.adedom.myfood.data.models.response.FoodDetailResponse

interface FoodRemoteDataSource {

    suspend fun callFoodListByCategoryId(categoryId: Int): BaseResponse<List<FoodDetailResponse>>
}