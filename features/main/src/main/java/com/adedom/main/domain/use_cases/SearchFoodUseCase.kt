package com.adedom.main.domain.use_cases

import com.adedom.main.domain.models.FoodModel
import com.adedom.main.domain.repositories.MainFoodRepository

class SearchFoodUseCase(
    private val mainFoodRepository: MainFoodRepository,
) {

    suspend operator fun invoke(search: String): List<FoodModel> {
        return mainFoodRepository.getFoodListBySearch(search).map { food ->
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