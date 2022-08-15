package com.adedom.main.domain.use_cases

import com.adedom.core.utils.ApiServiceException
import com.adedom.core.utils.Resource
import com.adedom.main.domain.models.CategoryModel
import com.adedom.main.domain.models.FoodModel
import com.adedom.main.domain.models.MainContentModel
import com.adedom.main.domain.repositories.HomeRepository
import com.adedom.main.domain.repositories.MainCategoryRepository
import com.adedom.myfood.data.models.response.CategoryResponse
import com.adedom.myfood.data.models.response.FoodDetailResponse
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class MainContentUseCase(
    private val homeRepository: HomeRepository,
    private val mainCategoryRepository: MainCategoryRepository,
) {

    suspend operator fun invoke(): Resource<MainContentModel> {
        return try {
            coroutineScope {
                val categoryAll = mainCategoryRepository.callCategoryAll()

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
                    .map { mapFoodToFoodModel(it) }
                    .filter { food ->
                        food.categoryId == CATEGORY_RECOMMEND
                    }

                val mainContentModel = MainContentModel(
                    categoryList = categoryList,
                    foodList = foodList,
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
            categoryId = category.categoryId,
            categoryName = category.categoryName,
            image = category.image,
        )
    }

    private fun mapFoodToFoodModel(food: FoodDetailResponse): FoodModel {
        return FoodModel(
            foodId = food.foodId,
            foodName = food.foodName,
            alias = "alias",
            image = food.image,
            ratingScoreCount = "4.9",
            categoryId = food.categoryId,
        )
    }

    companion object {
        const val CATEGORY_RECOMMEND = 1
    }
}