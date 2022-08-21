package com.adedom.search_food.presentation.event

sealed interface SearchFoodUiEvent {

    data class FoodDetail(
        val foodId: Long,
    ) : SearchFoodUiEvent
}