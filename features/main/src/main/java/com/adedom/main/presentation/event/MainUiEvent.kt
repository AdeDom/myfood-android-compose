package com.adedom.main.presentation.event

sealed interface MainUiEvent {

    object Logout : MainUiEvent

    object SearchFood : MainUiEvent

    data class FoodDetail(
        val foodId: Long,
    ) : MainUiEvent

    object OnBackAlert : MainUiEvent

    object OnBackPressed : MainUiEvent
}