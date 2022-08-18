package com.adedom.main.presentation.event

import com.adedom.main.domain.models.CategoryModel
import com.adedom.main.domain.models.FoodModel

sealed interface MainUiEvent {

    object Logout : MainUiEvent

    data class FoodDetail(
        val foodId: Int,
    ) : MainUiEvent

    data class SaveState(
        val categoryList: List<CategoryModel> = emptyList(),
        val foodList: List<FoodModel> = emptyList(),
    ) : MainUiEvent
}