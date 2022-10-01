package com.adedom.food_detail.domain.repositories

import com.myfood.server.data.models.response.FoodDetailResponse

interface FoodDetailRepository {

    suspend fun callFoodDetail(foodId: Int): FoodDetailResponse?
}