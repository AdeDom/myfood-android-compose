package com.adedom.food_detail.domain.use_cases

import com.adedom.data.repositories.FoodRepository
import com.adedom.food_detail.domain.models.FoodDetailModel
import com.myfood.server.data.models.response.FoodDetailResponse

class GetFoodDetailUseCase(
    private val foodRepository: FoodRepository,
) {

    suspend operator fun invoke(foodId: Int?): FoodDetailModel {
        val foodDetailResponse = foodRepository.callFoodDetail(
            foodId ?: throw Throwable("Food id is null"),
        )
        return mapFoodDetailResponseToFoodDetailModel(foodDetailResponse)
    }

    private fun mapFoodDetailResponseToFoodDetailModel(food: FoodDetailResponse?): FoodDetailModel {
        return FoodDetailModel(
            foodName = food?.foodName ?: "-",
            image = food?.image ?: "-",
            price = food?.price ?: 0.0,
            description = food?.description ?: "-",
            ratingScoreCount = food?.ratingScoreCount ?: "-",
        )
    }
}