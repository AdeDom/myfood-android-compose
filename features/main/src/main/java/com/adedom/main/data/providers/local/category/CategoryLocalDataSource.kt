package com.adedom.main.data.providers.local.category

import kotlinx.coroutines.flow.Flow
import myfood.database.CategoryEntity

interface CategoryLocalDataSource {

    fun getCategoryListFlow(): Flow<List<CategoryEntity>>

    suspend fun getCategoryList(): List<CategoryEntity>

    suspend fun getCategoryNameByCategoryId(categoryId: Long): String?

    suspend fun saveCategoryAll(categories: List<CategoryEntity>)

    suspend fun deleteCategoryAll()
}