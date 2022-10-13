package com.adedom.food_detail.presentation.view_model

import com.adedom.core.data.providers.data_store.AppDataStore
import com.adedom.core.utils.ApiServiceException
import com.adedom.core.utils.AuthRole
import com.adedom.core.utils.toBaseError
import com.adedom.domain.use_cases.*
import com.adedom.food_detail.domain.models.FoodDetailModel
import com.adedom.food_detail.domain.use_cases.GetFoodDetailUseCase
import com.adedom.ui_components.base.BaseViewModel
import com.myfood.server.data.models.base.BaseError
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

sealed interface FoodDetailUiState {
    object Loading : FoodDetailUiState
    data class Error(val error: BaseError) : FoodDetailUiState
    data class Success(
        val data: FoodDetailModel,
        val isFavorite: Boolean,
    ) : FoodDetailUiState
}

sealed interface FoodDetailUiEvent {
    data class MyFavorite(val foodId: Int?) : FoodDetailUiEvent
}

class FoodDetailViewModel(
    private val appDataStore: AppDataStore,
    private val getFoodDetailUseCase: GetFoodDetailUseCase,
    private val getMyFavoriteWebSocketFlowUseCase: GetMyFavoriteWebSocketFlowUseCase,
    private val updateFavoriteUseCase: UpdateFavoriteUseCase,
    private val sendMyFavoriteWebSocketUseCase: SendMyFavoriteWebSocketUseCase,
    private val closeFavoriteWebSocketUseCase: CloseFavoriteWebSocketUseCase,
    private val getIsActiveFavoriteWebSocketUseCase: GetIsActiveFavoriteWebSocketUseCase,
) : BaseViewModel<FoodDetailUiEvent, FoodDetailUiState>(FoodDetailUiState.Loading) {

    private val _message = Channel<String>()
    val message: Flow<String> = _message.receiveAsFlow()

    init {
        observeMyFavorite()
    }

    private fun observeMyFavorite() {
        launch {
            while (true) {
                if (getIsActiveFavoriteWebSocketUseCase()) {
                    getMyFavoriteWebSocketFlowUseCase().collect {
                        updateFavoriteUseCase(it)
                        _message.send(it?.favorite.toString())
                    }
                }
                delay(200)
            }
        }
    }

    fun callFoodDetail(foodId: Int?) {
        launch {
            try {
                val foodDetailModel = getFoodDetailUseCase(foodId)
                val isFavorite = appDataStore.getAuthRole() == AuthRole.Auth
                setState { FoodDetailUiState.Success(foodDetailModel, isFavorite) }
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
                    val result = sendMyFavoriteWebSocketUseCase(event.foodId)
                    if (!result) {
                        dispatch(FoodDetailUiEvent.MyFavorite(event.foodId))
                    }
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