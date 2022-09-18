package com.adedom.food_detail.domain.use_cases

import com.adedom.core.data.Resource
import com.adedom.core.data.models.error.AppErrorCode
import com.adedom.food_detail.domain.models.FoodDetailModel
import com.adedom.food_detail.domain.repositories.FoodDetailRepository
import com.adedom.myfood.data.models.base.BaseError
import com.adedom.myfood.data.models.response.FoodDetailResponse

class GetFoodDetailUseCase(
    private val foodDetailRepository: FoodDetailRepository,
) {

    suspend operator fun invoke(foodId: Int?): Resource<FoodDetailModel> {
        return if (foodId != null) {
            val foodDetailResult = foodDetailRepository.callFoodDetail(foodId)
            if (foodDetailResult is Resource.Success) {
                val foodModel = mapFoodDetailResponseToFoodDetailModel(foodDetailResult.data)
                Resource.Success(foodModel)
            } else {
                val baseError = BaseError(code = AppErrorCode.DataIsNull.code)
                Resource.Error(baseError)
            }
        } else {
            val error = BaseError(code = AppErrorCode.FoodIdIsNull.code)
            Resource.Error(error)
        }
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