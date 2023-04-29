package com.adedom.main.data.repositories

import com.adedom.main.data.providers.local.category.CategoryLocalDataSource
import com.adedom.main.data.providers.remote.category.CategoryRemoteDataSource
import com.adedom.main.domain.repositories.HomeCategoryRepository
import com.myfood.server.data.models.response.CategoryResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import myfood.database.CategoryEntity

class HomeCategoryRepositoryImpl(
    private val categoryLocalDataSource: CategoryLocalDataSource,
    private val categoryRemoteDataSource: CategoryRemoteDataSource,
    private val ioDispatcher: CoroutineDispatcher
) : HomeCategoryRepository {

    override suspend fun callCategoryAll(): List<CategoryResponse> {
        return withContext(ioDispatcher) {
            val categoryAllResponse = categoryRemoteDataSource.callCategoryAll()
            categoryAllResponse.result ?: emptyList()
        }
    }

    override fun getCategoryListFlow(): Flow<List<CategoryEntity>> {
        return categoryLocalDataSource.getCategoryListFlow().flowOn(ioDispatcher)
    }

    override suspend fun getCategoryList(): List<CategoryEntity> {
        return withContext(ioDispatcher) {
            categoryLocalDataSource.getCategoryList()
        }
    }

    override suspend fun getCategoryNameByCategoryId(categoryId: Long): String? {
        return withContext(ioDispatcher) {
            categoryLocalDataSource.getCategoryNameByCategoryId(categoryId)
        }
    }

    override suspend fun saveCategoryAll(categories: List<CategoryEntity>) {
        return withContext(ioDispatcher) {
            categoryLocalDataSource.saveCategoryAll(categories)
        }
    }

    override suspend fun deleteCategoryAll() {
        return withContext(ioDispatcher) {
            categoryLocalDataSource.deleteCategoryAll()
        }
    }
}