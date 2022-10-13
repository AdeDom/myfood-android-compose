package com.adedom.domain.use_cases

import com.adedom.data.repositories.FoodRepository
import com.adedom.ui_components.domain.models.FoodModel

class GetFoodListByCategoryIdFlowUseCase(
    private val foodRepository: FoodRepository,
) {

    suspend operator fun invoke(categoryId: Long): List<FoodModel> {
        return foodRepository.getFoodListByCategoryId(categoryId).map { food ->
            FoodModel(
                foodId = food.foodIdRef ?: 0L,
                foodName = food.foodName,
                alias = food.alias,
                image = food.image,
                favorite = food.favorite,
                ratingScoreCount = food.ratingScoreCount,
                categoryId = food.categoryId,
            )
        }
    }
}