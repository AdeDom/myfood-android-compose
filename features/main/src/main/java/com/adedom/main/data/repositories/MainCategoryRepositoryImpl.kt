package com.adedom.main.data.repositories

import com.adedom.main.data.providers.remote.category.CategoryRemoteDataSource
import com.adedom.main.domain.repositories.MainCategoryRepository
import com.adedom.myfood.data.models.response.CategoryResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainCategoryRepositoryImpl(
    private val categoryRemoteDataSource: CategoryRemoteDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : MainCategoryRepository {

    override suspend fun callCategoryAll(): List<CategoryResponse> {
        return withContext(ioDispatcher) {
            val categoryAllResponse = categoryRemoteDataSource.callCategoryAll()
            categoryAllResponse.result ?: emptyList()
        }
    }
}