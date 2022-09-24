package com.adedom.main.domain.repositories

import com.myfood.server.data.models.response.CategoryResponse
import kotlinx.coroutines.flow.Flow
import myfood.database.CategoryEntity

interface HomeCategoryRepository {

    suspend fun callCategoryAll(): List<CategoryResponse>

    fun getCategoryListFlow(): Flow<List<CategoryEntity>>

    suspend fun getCategoryList(): List<CategoryEntity>

    suspend fun getCategoryNameByCategoryId(categoryId: Long): String

    suspend fun saveCategoryAll(categories: List<CategoryEntity>)

    suspend fun deleteCategoryAll()
}