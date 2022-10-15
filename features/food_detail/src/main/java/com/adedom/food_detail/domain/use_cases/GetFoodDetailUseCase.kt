package com.adedom.food_detail.domain.use_cases

import com.adedom.core.data.providers.data_store.AppDataStore
import com.adedom.core.utils.AuthRole
import com.adedom.data.repositories.FavoriteRepository
import com.adedom.data.repositories.FoodRepository
import com.adedom.food_detail.domain.models.FoodDetailModel
import com.myfood.server.data.models.response.FoodDetailResponse

class GetFoodDetailUseCase(
    private val appDataStore: AppDataStore,
    private val foodRepository: FoodRepository,
    private val favoriteRepository: FavoriteRepository,
) {

    suspend operator fun invoke(foodId: Int?): FoodDetailModel {
        // call food detail
        val foodDetailResponse = foodRepository.callFoodDetail(
            foodId ?: throw Throwable("Food id is null"),
        )

        // isFavorite
        val isFavorite = appDataStore.getAuthRole() == AuthRole.Auth

        // isFavoriteState
        val favoriteCount = favoriteRepository.getIsFavoriteByFoodId(foodId.toLong()) ?: 0L
        val isFavoriteState = favoriteCount == 1L

        // mapper
        return mapFoodDetailResponseToFoodDetailModel(
            foodDetailResponse,
            isFavorite,
            isFavoriteState,
        )
    }

    private fun mapFoodDetailResponseToFoodDetailModel(
        food: FoodDetailResponse?,
        isFavorite: Boolean,
        isFavoriteState: Boolean,
    ): FoodDetailModel {
        return FoodDetailModel(
            foodName = food?.foodName ?: "-",
            image = food?.image ?: "-",
            price = food?.price ?: 0.0,
            description = food?.description ?: "-",
            favorite = food?.favorite ?: 0L,
            ratingScoreCount = food?.ratingScoreCount ?: "-",
            isFavorite = isFavorite,
            isFavoriteState = isFavoriteState,
        )
    }
}