package com.adedom.search_food.domain.use_cases

import com.adedom.core.domain.models.FoodModel
import com.adedom.search_food.domain.repositories.SearchFoodRepository

class SearchFoodUseCase(
    private val searchFoodRepository: SearchFoodRepository,
) {

    suspend operator fun invoke(search: String): List<FoodModel> {
        return searchFoodRepository.getFoodListBySearch(search).map { food ->
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