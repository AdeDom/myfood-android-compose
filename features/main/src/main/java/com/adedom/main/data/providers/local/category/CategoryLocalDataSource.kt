package com.adedom.main.data.providers.local.category

import kotlinx.coroutines.flow.Flow
import myfood.database.CategoryEntity

interface CategoryLocalDataSource {

    fun getCategoryListFlow(): Flow<List<CategoryEntity>>

    suspend fun getCategoryList(): List<CategoryEntity>

    suspend fun saveCategoryAll(categoryList: List<CategoryEntity>)

    suspend fun deleteCategoryAll()
}