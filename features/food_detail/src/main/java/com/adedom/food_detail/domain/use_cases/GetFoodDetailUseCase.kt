package com.adedom.food_detail.domain.use_cases

import com.adedom.core.data.providers.data_store.AppDataStore
import com.adedom.core.utils.AuthRole
import com.adedom.data.repositories.FoodRepository
import com.adedom.food_detail.domain.models.FoodDetailModel
import com.myfood.server.data.models.response.FoodDetailResponse

class GetFoodDetailUseCase(
    private val appDataStore: AppDataStore,
    private val foodRepository: FoodRepository,
) {

    suspend operator fun invoke(foodId: Int?): FoodDetailModel {
        val foodDetailResponse = foodRepository.callFoodDetail(
            foodId ?: throw Throwable("Food id is null"),
        )
        return mapFoodDetailResponseToFoodDetailModel(foodDetailResponse)
    }

    private suspend fun mapFoodDetailResponseToFoodDetailModel(food: FoodDetailResponse?): FoodDetailModel {
        return FoodDetailModel(
            foodName = food?.foodName ?: "-",
            image = food?.image ?: "-",
            price = food?.price ?: 0.0,
            description = food?.description ?: "-",
            favorite = food?.favorite ?: 0L,
            ratingScoreCount = food?.ratingScoreCount ?: "-",
            isFavorite = appDataStore.getAuthRole() == AuthRole.Auth
        )
    }
}