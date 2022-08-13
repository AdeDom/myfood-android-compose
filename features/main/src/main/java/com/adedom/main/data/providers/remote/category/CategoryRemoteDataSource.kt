package com.adedom.main.data.providers.remote.category

import com.adedom.myfood.data.models.base.BaseResponse
import com.adedom.myfood.data.models.response.CategoryResponse

interface CategoryRemoteDataSource {

    suspend fun callCategoryAll(): BaseResponse<List<CategoryResponse>>
}