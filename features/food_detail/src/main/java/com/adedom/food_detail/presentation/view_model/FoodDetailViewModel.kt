package com.adedom.food_detail.presentation.view_model

import com.adedom.core.base.BaseViewModel
import com.adedom.food_detail.presentation.event.FoodDetailUiEvent
import com.adedom.food_detail.presentation.state.FoodDetailUiState

class FoodDetailViewModel : BaseViewModel<FoodDetailUiState, FoodDetailUiEvent>(
    FoodDetailUiState()
) {

    fun setFoodId(foodId: Int?) {
        uiState = uiState.copy(foodId = foodId)
    }
}