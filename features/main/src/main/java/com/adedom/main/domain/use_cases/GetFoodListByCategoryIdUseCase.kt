package com.adedom.main.domain.use_cases

import com.adedom.main.domain.models.FoodModel
import com.adedom.main.domain.repositories.MainCategoryRepository
import com.adedom.main.domain.repositories.MainFoodRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class GetFoodListByCategoryIdUseCase(
    private val mainCategoryRepository: MainCategoryRepository,
    private val mainFoodRepository: MainFoodRepository,
) {

    suspend operator fun invoke(categoryId: Long): Pair<String, List<FoodModel>> {
        return coroutineScope {
            val categoryNameAsync = async {
                mainCategoryRepository.getCategoryNameByCategoryId(categoryId)
            }

            val foodListAsync = async {
                mainFoodRepository.getFoodListByCategoryId(categoryId).map { food ->
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