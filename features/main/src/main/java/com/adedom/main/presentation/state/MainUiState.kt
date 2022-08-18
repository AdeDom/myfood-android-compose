package com.adedom.main.presentation.state

import com.adedom.main.domain.models.FoodModel
import com.adedom.main.domain.models.MainContentModel
import com.adedom.myfood.data.models.base.BaseError

data class MainUiState(
    val isLoading: Boolean = false,
    val error: BaseError? = null,
    val foodList: List<FoodModel> = emptyList(),
    val mainContent: MainContentModel? = null,
)