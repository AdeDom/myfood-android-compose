package com.adedom.main.presentation.event

import com.adedom.core.domain.models.FoodModel
import com.adedom.main.domain.models.CategoryModel

sealed interface MainUiEvent {

    object Logout : MainUiEvent

    object SearchFood : MainUiEvent

    data class FoodDetail(
        val foodId: Long,
    ) : MainUiEvent

    data class SaveState(
        val categories: List<CategoryModel> = emptyList(),
        val categoryName: String = "",
        val foods: List<FoodModel> = emptyList(),
    ) : MainUiEvent
}