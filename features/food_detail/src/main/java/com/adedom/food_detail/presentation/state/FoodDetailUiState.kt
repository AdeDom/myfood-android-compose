package com.adedom.food_detail.presentation.state

data class FoodDetailUiState(
    val isLoading: Boolean = false,
    val foodId: Int? = null,
)