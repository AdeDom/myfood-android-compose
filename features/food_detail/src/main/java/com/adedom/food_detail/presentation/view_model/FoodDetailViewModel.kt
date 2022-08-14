package com.adedom.food_detail.presentation.view_model

import com.adedom.core.utils.Resource
import com.adedom.food_detail.domain.use_cases.GetFoodDetailUseCase
import com.adedom.food_detail.presentation.event.FoodDetailUiEvent
import com.adedom.food_detail.presentation.state.FoodDetailUiState
import com.adedom.ui_components.base.BaseViewModel

class FoodDetailViewModel(
    private val getFoodDetailUseCase: GetFoodDetailUseCase,
) : BaseViewModel<FoodDetailUiState, FoodDetailUiEvent>(FoodDetailUiState()) {

    fun callFoodDetail(foodId: Int?) {
        launch {
            uiState = uiState.copy(isLoading = true)

            val resource = getFoodDetailUseCase(foodId)
            uiState = when (resource) {
                is Resource.Success -> {
                    uiState.copy(
                        isLoading = false,
                        foodDetail = resource.data,
                    )
                }
                is Resource.Error -> {
                    uiState.copy(
                        isLoading = false,
                        error = resource.error,
                    )
                }
            }
        }
    }

    fun setOnBackPressedEvent() {
        launch {
            val event = FoodDetailUiEvent.OnBackPressed
            _uiEvent.emit(event)
        }
    }
}