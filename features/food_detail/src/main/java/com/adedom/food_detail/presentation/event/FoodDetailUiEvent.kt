package com.adedom.food_detail.presentation.event

sealed interface FoodDetailUiEvent {
    object OnBackPressed : FoodDetailUiEvent
}