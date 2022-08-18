package com.adedom.main.presentation.event

import com.adedom.main.domain.models.MainContentModel

sealed interface MainUiEvent {

    object Logout : MainUiEvent

    data class FoodDetail(
        val foodId: Int,
    ) : MainUiEvent

    data class SaveState(
        val mainContent: MainContentModel? = null,
    ) : MainUiEvent
}