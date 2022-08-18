package com.adedom.main.domain.use_cases

import com.adedom.main.domain.models.FoodModel
import com.adedom.main.domain.repositories.MainFoodRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetFoodUseCase(
    private val mainFoodRepository: MainFoodRepository,
) {

    operator fun invoke(): Flow<List<FoodModel>> {
        return mainFoodRepository.getFoodListFlow()
            .map { foodList ->
                foodList.map { food ->
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
}