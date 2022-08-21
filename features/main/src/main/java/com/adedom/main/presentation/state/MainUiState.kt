package com.adedom.main.presentation.state

import com.adedom.core.domain.models.FoodModel
import com.adedom.main.domain.models.CategoryModel
import com.adedom.myfood.data.models.base.BaseError

data class MainUiState(
    val isLoading: Boolean = false,
    val error: BaseError? = null,
    val categories: List<CategoryModel> = emptyList(),
    val categoryName: String = "",
    val foods: List<FoodModel> = emptyList(),
    val searchList: List<FoodModel> = emptyList(),
)