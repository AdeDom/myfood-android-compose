package com.adedom.food_detail.presentation.view_model

import androidx.lifecycle.viewModelScope
import com.adedom.core.utils.Resource
import com.adedom.food_detail.domain.models.FoodDetailModel
import com.adedom.food_detail.domain.use_cases.GetFoodDetailUseCase
import com.adedom.myfood.data.models.base.BaseError
import com.adedom.ui_components.base.BaseViewModel
import kotlinx.coroutines.launch

data class FoodDetailUiState(
    val isLoading: Boolean = false,
    val foodDetail: FoodDetailModel? = null,
    val error: BaseError? = null,
)

sealed interface FoodDetailUiEvent {
    object OnBackPressed : FoodDetailUiEvent
}

sealed interface FoodDetailUiAction {
    object OnBackPressed : FoodDetailUiAction
}

class FoodDetailViewModel(
    private val getFoodDetailUseCase: GetFoodDetailUseCase,
) : BaseViewModel<FoodDetailUiState, FoodDetailUiEvent>(FoodDetailUiState()) {

    fun callFoodDetail(foodId: Int?) {
        viewModelScope.launch {
            uiState = uiState.copy(
                isLoading = true,
                error = null,
            )

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

    fun dispatch(action: FoodDetailUiAction) {
        viewModelScope.launch {
            when (action) {
                FoodDetailUiAction.OnBackPressed -> {
                    _uiEvent.emit(FoodDetailUiEvent.OnBackPressed)
                }
            }
        }
    }
}