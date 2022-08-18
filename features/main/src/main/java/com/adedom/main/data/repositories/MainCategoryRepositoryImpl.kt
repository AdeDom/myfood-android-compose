package com.adedom.main.data.repositories

import com.adedom.main.data.providers.local.category.CategoryLocalDataSource
import com.adedom.main.data.providers.remote.category.CategoryRemoteDataSource
import com.adedom.main.domain.repositories.MainCategoryRepository
import com.adedom.myfood.data.models.response.CategoryResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import myfood.database.CategoryEntity

class MainCategoryRepositoryImpl(
    private val categoryLocalDataSource: CategoryLocalDataSource,
    private val categoryRemoteDataSource: CategoryRemoteDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : MainCategoryRepository {

    override suspend fun callCategoryAll(): List<CategoryResponse> {
        return withContext(ioDispatcher) {
            val categoryAllResponse = categoryRemoteDataSource.callCategoryAll()
            categoryAllResponse.result ?: emptyList()
        }
    }

    override fun getCategoryListFlow(): Flow<List<CategoryEntity>> {
        return categoryLocalDataSource.getCategoryListFlow()
    }

    override suspend fun getCategoryList(): List<CategoryEntity> {
        return withContext(ioDispatcher) {
            categoryLocalDataSource.getCategoryList()
        }
    }

    override suspend fun saveCategoryAll(categoryList: List<CategoryEntity>) {
        return withContext(ioDispatcher) {
            categoryLocalDataSource.saveCategoryAll(categoryList)
        }
    }

    override suspend fun deleteCategoryAll() {
        return withContext(ioDispatcher) {
            categoryLocalDataSource.deleteCategoryAll()
        }
    }
}