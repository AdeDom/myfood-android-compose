package com.adedom.food_detail.presentation.view_model

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

sealed interface FoodDetailUiEvent

class FoodDetailViewModel(
    private val getFoodDetailUseCase: GetFoodDetailUseCase,
) : BaseViewModel<FoodDetailUiEvent, FoodDetailUiState>(FoodDetailUiState()) {

    fun callFoodDetail(foodId: Int?) {
        launch {
            setState {
                copy(
                    isLoading = true,
                    error = null,
                )
            }

            val resource = getFoodDetailUseCase(foodId)
            when (resource) {
                is Resource.Success -> {
                    setState {
                        copy(
                            isLoading = false,
                            foodDetail = resource.data,
                        )
                    }
                }
                is Resource.Error -> {
                    setState {
                        copy(
                            isLoading = false,
                            error = resource.error,
                        )
                    }
                }
            }
        }
    }

    override fun dispatch(event: FoodDetailUiEvent) {}
}