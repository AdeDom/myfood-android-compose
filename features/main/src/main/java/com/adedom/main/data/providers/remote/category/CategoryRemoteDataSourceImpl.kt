package com.adedom.main.data.providers.remote.category

import com.adedom.main.BuildConfig
import com.adedom.myfood.data.models.base.BaseResponse
import com.adedom.myfood.data.models.response.CategoryResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class CategoryRemoteDataSourceImpl(
    private val httpClient: HttpClient,
) : CategoryRemoteDataSource {

    override suspend fun callCategoryAll(): BaseResponse<List<CategoryResponse>> {
        return httpClient
            .get(BuildConfig.BASE_URL + "api/category/getCategoryAll")
            .body()
    }
}