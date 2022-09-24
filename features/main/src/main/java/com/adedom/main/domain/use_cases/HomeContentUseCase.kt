package com.adedom.main.domain.use_cases

import com.adedom.core.data.Resource
import com.adedom.main.domain.models.CategoryModel
import com.adedom.main.domain.repositories.HomeCategoryRepository
import com.adedom.main.domain.repositories.HomeFoodRepository
import com.myfood.server.data.models.response.CategoryResponse
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import myfood.database.CategoryEntity
import myfood.database.FoodEntity

class HomeContentUseCase(
    private val homeCategoryRepository: HomeCategoryRepository,
    private val homeFoodRepository: HomeFoodRepository,
) {

    suspend operator fun invoke(): Resource<List<CategoryModel>> {
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
            val foods = categories
                .map { category ->
                    async {
                        homeFoodRepository.callFoodListByCategoryId(category.categoryId)
                    }
                }
                .awaitAll()
                .flatten()
            val foodEntity = foods.map { food ->
                FoodEntity(
                    alias = food.alias,
                    categoryId = food.categoryId.toLong(),
                    created = food.created,
                    description = food.description,
                    favorite = food.favorite,
                    foodId = food.foodId.toLong(),
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
            homeFoodRepository.deleteFoodAll()
            homeFoodRepository.saveFoodAll(foodEntity)

            val categoriesModel = categories.map { mapCategoryToCategoryModel(it) }
            Resource.Success(categoriesModel)
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