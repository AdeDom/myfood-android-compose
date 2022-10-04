package com.adedom.food_detail.presentation.view_model

import com.adedom.core.utils.ApiServiceException
import com.adedom.core.utils.toBaseError
import com.adedom.domain.use_cases.CloseFavoriteWebSocketUseCase
import com.adedom.domain.use_cases.GetMyFavoriteWebSocketFlowUseCase
import com.adedom.domain.use_cases.SendMyFavoriteWebSocketUseCase
import com.adedom.food_detail.domain.models.FoodDetailModel
import com.adedom.food_detail.domain.use_cases.GetFoodDetailUseCase
import com.adedom.ui_components.base.BaseViewModel
import com.myfood.server.data.models.base.BaseError
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

sealed interface FoodDetailUiState {
    object Loading : FoodDetailUiState
    data class Error(val error: BaseError) : FoodDetailUiState
    data class Success(val data: FoodDetailModel) : FoodDetailUiState
}

sealed interface FoodDetailUiEvent {
    data class MyFavorite(val foodId: Int?) : FoodDetailUiEvent
}

class FoodDetailViewModel(
    private val getFoodDetailUseCase: GetFoodDetailUseCase,
    private val getMyFavoriteWebSocketFlowUseCase: GetMyFavoriteWebSocketFlowUseCase,
    private val sendMyFavoriteWebSocketUseCase: SendMyFavoriteWebSocketUseCase,
    private val closeFavoriteWebSocketUseCase: CloseFavoriteWebSocketUseCase,
) : BaseViewModel<FoodDetailUiEvent, FoodDetailUiState>(FoodDetailUiState.Loading) {

    private val _message = Channel<String>()
    val message: Flow<String> = _message.receiveAsFlow()

    init {
        observeMyFavorite()
    }

    private fun observeMyFavorite() {
        launch {
            getMyFavoriteWebSocketFlowUseCase().collect {
                _message.send(it?.favorite.toString())
            }
            observeMyFavorite()
        }
    }

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

    override fun dispatch(event: FoodDetailUiEvent) {
        when (event) {
            is FoodDetailUiEvent.MyFavorite -> {
                launch {
                    sendMyFavoriteWebSocketUseCase(event.foodId)
                }
            }
        }
    }

    override fun onCleared() {
        launch {
            closeFavoriteWebSocketUseCase()
            super.onCleared()
        }
    }
}