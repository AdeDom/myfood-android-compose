package com.adedom.main.domain.use_cases

import com.adedom.main.domain.models.CategoryModel
import com.adedom.main.domain.repositories.MainCategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetCategoryUseCase(
    private val mainCategoryRepository: MainCategoryRepository,
) {

    operator fun invoke(): Flow<List<CategoryModel>> {
        return mainCategoryRepository.getCategoryListFlow()
            .map { categories ->
                categories.map { category ->
                    CategoryModel(
                        categoryId = category.categoryId,
                        categoryName = category.categoryName,
                        image = category.image,
                    )
                }
            }
    }
}