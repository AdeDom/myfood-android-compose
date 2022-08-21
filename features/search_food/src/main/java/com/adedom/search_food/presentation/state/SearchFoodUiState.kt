package com.adedom.search_food.presentation.state

import com.adedom.core.domain.models.FoodModel

data class SearchFoodUiState(
    val search: String = "",
    val searchList: List<FoodModel> = emptyList(),
)