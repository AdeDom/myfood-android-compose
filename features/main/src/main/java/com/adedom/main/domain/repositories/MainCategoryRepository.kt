package com.adedom.main.domain.repositories

import com.adedom.myfood.data.models.response.CategoryResponse

interface MainCategoryRepository {

    suspend fun callCategoryAll(): List<CategoryResponse>
}