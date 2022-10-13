package com.adedom.domain.use_cases

import com.adedom.data.repositories.FoodRepository
import com.adedom.ui_components.domain.models.FoodModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetFoodListFlowUseCase(
    private val foodRepository: FoodRepository,
) {

    operator fun invoke(categoryId: Long): Flow<List<FoodModel>> {
        return foodRepository.getFoodListByCategoryIdFlow(categoryId).map {
            it.map { food ->
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
}