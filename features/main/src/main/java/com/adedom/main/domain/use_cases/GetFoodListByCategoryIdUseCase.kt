package com.adedom.main.domain.use_cases

import com.adedom.data.repositories.FoodRepository
import com.adedom.main.domain.repositories.HomeCategoryRepository
import com.adedom.ui_components.domain.models.FoodModel
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class GetFoodListByCategoryIdUseCase(
    private val homeCategoryRepository: HomeCategoryRepository,
    private val foodRepository: FoodRepository,
) {

    suspend operator fun invoke(categoryId: Long): Pair<String, List<FoodModel>> {
        return coroutineScope {
            val categoryNameAsync = async {
                homeCategoryRepository.getCategoryNameByCategoryId(categoryId)
            }

            val foodListAsync = async {
                foodRepository.getFoodListByCategoryId(categoryId).map { food ->
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

            Pair(categoryNameAsync.await(), foodListAsync.await())
        }
    }
}