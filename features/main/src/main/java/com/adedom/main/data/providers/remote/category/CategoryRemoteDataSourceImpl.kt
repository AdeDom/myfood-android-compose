package com.adedom.main.data.providers.remote.category

import com.adedom.core.data.providers.remote.DataProviderRemote
import com.adedom.main.BuildConfig
import com.adedom.myfood.data.models.base.BaseResponse
import com.adedom.myfood.data.models.response.CategoryResponse
import io.ktor.client.call.*
import io.ktor.client.request.*

class CategoryRemoteDataSourceImpl(
    private val dataProviderRemote: DataProviderRemote,
) : CategoryRemoteDataSource {

    override suspend fun callCategoryAll(): BaseResponse<List<CategoryResponse>> {
        return dataProviderRemote.getHttpClient()
            .get(BuildConfig.BASE_URL + "api/category/getCategoryAll")
            .body()
    }
}