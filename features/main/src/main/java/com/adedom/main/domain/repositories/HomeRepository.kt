package com.adedom.main.domain.repositories

import com.adedom.myfood.data.models.response.FoodDetailResponse

interface HomeRepository {

    suspend fun callFoodListByCategoryId(categoryId: Int): List<FoodDetailResponse>
}