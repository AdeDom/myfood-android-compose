package com.adedom.search_food.domain.use_cases

import com.adedom.data.repositories.FoodRepository
import com.adedom.ui_components.domain.models.FoodModel

class SearchFoodUseCase(
    private val foodRepository: FoodRepository,
) {

    suspend operator fun invoke(search: String): List<FoodModel> {
        return foodRepository.getFoodListBySearch(search).map { food ->
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