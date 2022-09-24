package com.adedom.main.data.providers.remote.category

import com.myfood.server.data.models.base.BaseResponse
import com.myfood.server.data.models.response.CategoryResponse

interface CategoryRemoteDataSource {

    suspend fun callCategoryAll(): BaseResponse<List<CategoryResponse>>
}