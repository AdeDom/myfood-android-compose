package com.adedom.food_detail.presentation.view_model

import com.adedom.core.data.Resource
import com.adedom.food_detail.domain.models.FoodDetailModel
import com.adedom.food_detail.domain.use_cases.GetFoodDetailUseCase
import com.adedom.ui_components.base.BaseViewModel
import com.myfood.server.data.models.base.BaseError
import kotlinx.coroutines.launch

sealed interface FoodDetailUiState {
    object Loading : FoodDetailUiState
    data class Error(val error: BaseError) : FoodDetailUiState
    data class Success(val data: FoodDetailModel) : FoodDetailUiState
}

sealed interface FoodDetailUiEvent

class FoodDetailViewModel(
    private val getFoodDetailUseCase: GetFoodDetailUseCase,
) : BaseViewModel<FoodDetailUiEvent, FoodDetailUiState>(FoodDetailUiState.Loading) {

    fun callFoodDetail(foodId: Int?) {
        launch {
            val resource = getFoodDetailUseCase(foodId)
            when (resource) {
                is Resource.Success -> {
                    setState { FoodDetailUiState.Success(resource.data) }
                }
                is Resource.Error -> {
                    setState { FoodDetailUiState.Error(resource.error) }
                }
            }
        }
    }

    override fun dispatch(event: FoodDetailUiEvent) {}
}