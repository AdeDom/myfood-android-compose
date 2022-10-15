package com.adedom.food_detail.presentation.view_model

import com.adedom.core.utils.ApiServiceException
import com.adedom.core.utils.toBaseError
import com.adedom.domain.use_cases.GetIsActiveFavoriteWebSocketUseCase
import com.adedom.domain.use_cases.GetMyFavoriteWebSocketFlowUseCase
import com.adedom.domain.use_cases.SendMyFavoriteWebSocketUseCase
import com.adedom.food_detail.domain.models.FoodDetailModel
import com.adedom.food_detail.domain.use_cases.GetFavoriteFlowUseCase
import com.adedom.food_detail.domain.use_cases.GetFoodDetailUseCase
import com.adedom.food_detail.domain.use_cases.InsertOrReplaceFavoriteUseCase
import com.adedom.food_detail.domain.use_cases.UpdateBackupFavoriteUseCase
import com.adedom.ui_components.base.BaseViewModel
import com.myfood.server.data.models.base.BaseError
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

data class FoodDetailUiState(
    val foodDetail: FoodDetailModel? = null,
    val dialog: Dialog? = null,
) {
    sealed interface Dialog {
        object Loading : Dialog
        data class Error(val error: BaseError) : Dialog
    }
}

sealed interface FoodDetailUiEvent {
    data class MyFavorite(val foodId: Int?) : FoodDetailUiEvent
}

class FoodDetailViewModel(
    private val getFavoriteFlowUseCase: GetFavoriteFlowUseCase,
    private val getFoodDetailUseCase: GetFoodDetailUseCase,
    private val insertOrReplaceFavoriteUseCase: InsertOrReplaceFavoriteUseCase,
    private val sendMyFavoriteWebSocketUseCase: SendMyFavoriteWebSocketUseCase,
    private val updateBackupFavoriteUseCase: UpdateBackupFavoriteUseCase,
    private val getIsActiveFavoriteWebSocketUseCase: GetIsActiveFavoriteWebSocketUseCase,
    private val getMyFavoriteWebSocketFlowUseCase: GetMyFavoriteWebSocketFlowUseCase,
) : BaseViewModel<FoodDetailUiEvent, FoodDetailUiState>(FoodDetailUiState()) {

    init {
        observeMyFavorite()
    }

    private fun observeMyFavorite() {
        launch {
            while (true) {
                if (getIsActiveFavoriteWebSocketUseCase()) {
                    getMyFavoriteWebSocketFlowUseCase().collect {
                        setState {
                            copy(
                                foodDetail = foodDetail?.copy(favorite = it?.favorite ?: 0L)
                            )
                        }
                    }
                }
                delay(200)
            }
        }
    }

    fun observeFavoriteState(foodId: Int?) {
        getFavoriteFlowUseCase(foodId)
            .onEach {
                setState {
                    copy(
                        foodDetail = foodDetail?.copy(isFavoriteState = it)
                    )
                }
            }
            .launchIn(this)
    }

    fun callFoodDetail(foodId: Int?) {
        launch {
            try {
                setState {
                    copy(dialog = FoodDetailUiState.Dialog.Loading)
                }
                val foodDetailModel = getFoodDetailUseCase(foodId)
                setState {
                    copy(
                        foodDetail = foodDetailModel,
                        dialog = null,
                    )
                }
            } catch (exception: ApiServiceException) {
                setState {
                    copy(dialog = FoodDetailUiState.Dialog.Error(exception.toBaseError()))
                }
            } catch (exception: Throwable) {
                setState {
                    copy(dialog = FoodDetailUiState.Dialog.Error(exception.toBaseError()))
                }
            }
        }
    }

    override fun dispatch(event: FoodDetailUiEvent) {
        when (event) {
            is FoodDetailUiEvent.MyFavorite -> {
                launch {
                    try {
                        if (getIsActiveFavoriteWebSocketUseCase()) {
                            val favoriteId = insertOrReplaceFavoriteUseCase(event.foodId)
                            sendMyFavoriteWebSocketUseCase(event.foodId)
                            updateBackupFavoriteUseCase(favoriteId)
                        } else {
                            setState {
                                copy(dialog = FoodDetailUiState.Dialog.Error(BaseError()))
                            }
                        }
                    } catch (exception: Throwable) {
                        setState {
                            copy(dialog = FoodDetailUiState.Dialog.Error(exception.toBaseError()))
                        }
                    }
                }
            }
        }
    }
}