package com.adedom.main.presentation.state

import com.adedom.main.domain.models.CategoryModel
import com.adedom.main.domain.models.MainContentModel
import com.adedom.myfood.data.models.base.BaseError

data class MainUiState(
    val isLoading: Boolean = false,
    val error: BaseError? = null,
    val categoryList: List<CategoryModel> = emptyList(),
    val mainContent: MainContentModel? = null,
)