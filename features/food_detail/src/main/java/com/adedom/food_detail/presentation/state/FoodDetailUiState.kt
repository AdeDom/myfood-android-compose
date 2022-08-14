package com.adedom.food_detail.presentation.state

import com.adedom.food_detail.domain.models.FoodDetailModel
import com.adedom.myfood.data.models.base.BaseError

data class FoodDetailUiState(
    val isLoading: Boolean = false,
    val foodDetail: FoodDetailModel? = null,
    val error: BaseError? = null,
)