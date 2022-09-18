package com.adedom.search_food.domain.use_cases

import com.adedom.search_food.domain.repositories.SearchFoodRepository
import com.adedom.ui_components.domain.models.FoodModel

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