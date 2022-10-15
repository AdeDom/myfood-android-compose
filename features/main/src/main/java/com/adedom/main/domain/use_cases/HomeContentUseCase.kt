package com.adedom.main.domain.use_cases

import com.adedom.data.repositories.FoodRepository
import com.adedom.main.domain.models.CategoryModel
import com.adedom.main.domain.repositories.HomeCategoryRepository
import com.myfood.server.data.models.response.CategoryResponse
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import myfood.database.CategoryEntity
import myfood.database.FoodEntity

class HomeContentUseCase(
    private val homeCategoryRepository: HomeCategoryRepository,
    private val foodRepository: FoodRepository,
) {

    suspend operator fun invoke(): List<CategoryModel> {
        return coroutineScope {
            // category
            val categories = homeCategoryRepository.callCategoryAll()
            val categoryEntity = categories.map { category ->
                CategoryEntity(
                    categoryId = category.categoryId.toLong(),
                    categoryName = category.categoryName,
                    categoryTypeName = category.categoryTypeName,
                    created = category.created,
                    image = category.image,
                    updated = category.updated,
                )
            }
            homeCategoryRepository.deleteCategoryAll()
            homeCategoryRepository.saveCategoryAll(categoryEntity)

            // food
            val foodEntity = categories
                .map { category ->
                    async {
                        Pair(
                            category.categoryId,
                            foodRepository.callFoodListByCategoryId(category.categoryId),
                        )
                    }
                }
                .awaitAll()
                .flatMap { (categoryId, foods) ->
                    foods.map { food ->
                        FoodEntity(
                            alias = food.alias,
                            categoryId = categoryId.toLong(),
                            created = food.created,
                            description = food.description,
                            favorite = food.favorite,
                            foodIdKey = "${categoryId}_${food.foodId}",
                            foodIdRef = food.foodId.toLong(),
                            foodName = food.foodName,
                            search = food.foodName,
                            image = food.image,
                            price = food.price,
                            ratingScore = food.ratingScore?.toDouble(),
                            ratingScoreCount = food.ratingScoreCount,
                            status = food.status,
                            updated = food.updated,
                        )
                    }
                }
            foodRepository.deleteFoodAll()
            foodRepository.saveFoodAll(foodEntity)

            categories.map { mapCategoryToCategoryModel(it) }
        }
    }

    private fun mapCategoryToCategoryModel(category: CategoryResponse): CategoryModel {
        return CategoryModel(
            categoryId = category.categoryId.toLong(),
            categoryName = category.categoryName,
            image = category.image,
        )
    }
}