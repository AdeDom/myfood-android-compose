package com.adedom.main.domain.use_cases

import com.adedom.core.utils.ApiServiceException
import com.adedom.core.utils.Resource
import com.adedom.main.domain.models.CategoryModel
import com.adedom.main.domain.models.FoodModel
import com.adedom.main.domain.models.MainContentModel
import com.adedom.main.domain.repositories.HomeRepository
import com.adedom.main.domain.repositories.MainCategoryRepository
import com.adedom.main.domain.repositories.MainFoodRepository
import com.adedom.myfood.data.models.response.CategoryResponse
import com.adedom.myfood.data.models.response.FoodDetailResponse
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import myfood.database.CategoryEntity
import myfood.database.FoodEntity

class MainContentUseCase(
    private val homeRepository: HomeRepository,
    private val mainCategoryRepository: MainCategoryRepository,
    private val mainFoodRepository: MainFoodRepository,
) {

    suspend operator fun invoke(): Resource<MainContentModel> {
        return try {
            coroutineScope {
                val categoryAll = mainCategoryRepository.callCategoryAll()
                val categoryEntity = categoryAll.map { category ->
                    CategoryEntity(
                        categoryId = category.categoryId.toLong(),
                        categoryName = category.categoryName,
                        categoryTypeName = category.categoryTypeName,
                        created = category.created,
                        image = category.image,
                        updated = category.updated,
                    )
                }
                mainCategoryRepository.deleteCategoryAll()
                mainCategoryRepository.saveCategoryAll(categoryEntity)

                val categoryList = categoryAll.map { mapCategoryToCategoryModel(it) }

                val foodList = categoryAll
                    .map { category ->
                        async {
                            homeRepository.callFoodListByCategoryId(
                                categoryId = category.categoryId
                            )
                        }
                    }
                    .awaitAll()
                    .flatten()

                val foodEntity = foodList.map { food ->
                    FoodEntity(
                        alias = food.alias,
                        categoryId = food.categoryId.toLong(),
                        created = food.created,
                        description = food.description,
                        favorite = food.favorite,
                        foodId = food.foodId.toLong(),
                        foodName = food.foodName,
                        image = food.image,
                        price = food.price,
                        ratingScore = food.ratingScore?.toDouble(),
                        ratingScoreCount = food.ratingScoreCount,
                        status = food.status,
                        updated = food.updated,
                    )
                }
                mainFoodRepository.deleteFoodAll()
                mainFoodRepository.saveFoodAll(foodEntity)

                val mainContentModel = MainContentModel(
                    categoryList = categoryList,
                    foodList = foodList.map { mapFoodToFoodModel(it) },
                )
                Resource.Success(mainContentModel)
            }
        } catch (exception: ApiServiceException) {
            val baseError = exception.toBaseError()
            Resource.Error(baseError)
        }
    }

    private fun mapCategoryToCategoryModel(category: CategoryResponse): CategoryModel {
        return CategoryModel(
            categoryId = category.categoryId.toLong(),
            categoryName = category.categoryName,
            image = category.image,
        )
    }

    private fun mapFoodToFoodModel(food: FoodDetailResponse): FoodModel {
        return FoodModel(
            foodId = food.foodId.toLong(),
            foodName = food.foodName,
            alias = "alias",
            image = food.image,
            ratingScoreCount = "4.9",
            categoryId = food.categoryId.toLong(),
        )
    }

    companion object {
        const val CATEGORY_RECOMMEND = 1
    }
}