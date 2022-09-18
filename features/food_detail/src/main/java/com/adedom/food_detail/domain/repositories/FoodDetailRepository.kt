package com.adedom.food_detail.domain.repositories

import com.adedom.core.data.Resource
import com.adedom.myfood.data.models.response.FoodDetailResponse

interface FoodDetailRepository {

    suspend fun callFoodDetail(foodId: Int): Resource<FoodDetailResponse>
}