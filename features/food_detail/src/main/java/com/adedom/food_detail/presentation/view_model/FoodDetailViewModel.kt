package com.adedom.food_detail.presentation.view_model

import com.adedom.core.utils.ApiServiceException
import com.adedom.core.utils.toBaseError
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
            try {
                val foodDetailModel = getFoodDetailUseCase(foodId)
                setState { FoodDetailUiState.Success(foodDetailModel) }
            } catch (exception: ApiServiceException) {
                setState { FoodDetailUiState.Error(exception.toBaseError()) }
            } catch (exception: Throwable) {
                setState { FoodDetailUiState.Error(exception.toBaseError()) }
            }
        }
    }

    override fun dispatch(event: FoodDetailUiEvent) {}
}