package com.adedom.food_detail.domain.use_cases

import com.adedom.food_detail.domain.models.FoodDetailModel
import com.adedom.food_detail.domain.repositories.FoodDetailRepository
import com.myfood.server.data.models.response.FoodDetailResponse

class GetFoodDetailUseCase(
    private val foodDetailRepository: FoodDetailRepository,
) {

    suspend operator fun invoke(foodId: Int?): FoodDetailModel {
        val foodDetailResponse = foodDetailRepository.callFoodDetail(
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