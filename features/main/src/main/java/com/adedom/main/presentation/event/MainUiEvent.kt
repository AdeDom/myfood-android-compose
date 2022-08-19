package com.adedom.main.presentation.event

import com.adedom.main.domain.models.CategoryModel
import com.adedom.main.domain.models.FoodModel

sealed interface MainUiEvent {

    object Logout : MainUiEvent

    data class FoodDetail(
        val foodId: Long,
    ) : MainUiEvent

    data class SaveState(
        val categories: List<CategoryModel> = emptyList(),
        val categoryName: String = "",
        val foods: List<FoodModel> = emptyList(),
    ) : MainUiEvent
}