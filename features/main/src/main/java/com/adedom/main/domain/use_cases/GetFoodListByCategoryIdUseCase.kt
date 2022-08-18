package com.adedom.main.domain.use_cases

import com.adedom.main.domain.models.FoodModel
import com.adedom.main.domain.repositories.MainFoodRepository

class GetFoodListByCategoryIdUseCase(
    private val mainFoodRepository: MainFoodRepository,
) {

    suspend operator fun invoke(categoryId: Long): List<FoodModel> {
        return mainFoodRepository.getFoodListByCategoryId(categoryId).map { food ->
            FoodModel(
                foodId = food.foodId,
                foodName = food.foodName,
                alias = food.alias,
                image = food.image,
                ratingScoreCount = food.ratingScoreCount,
                categoryId = food.categoryId,
            )
        }
    }
}