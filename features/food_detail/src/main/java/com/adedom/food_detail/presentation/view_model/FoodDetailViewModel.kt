package com.adedom.food_detail.presentation.view_model

import com.adedom.core.utils.ApiServiceException
import com.adedom.core.utils.toBaseError
import com.adedom.data.providers.web_sockets.FavoriteWebSocketDataSource
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
    private val favoriteWebSocketDataSource: FavoriteWebSocketDataSource,
) : BaseViewModel<FoodDetailUiEvent, FoodDetailUiState>(FoodDetailUiState.Loading) {

    private val _message = Channel<String>()
    val message: Flow<String> = _message.receiveAsFlow()

    init {
        setupMyFavorite()
    }

    private fun setupMyFavorite() {
        launch {
            favoriteWebSocketDataSource.init()
            val isActive = favoriteWebSocketDataSource.isActive()
            if (isActive) {
                favoriteWebSocketDataSource.observe().collect {
                    _message.send(it.result?.favorite.toString())
                }
                setupMyFavorite()
            } else {
                setupMyFavorite()
            }
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
                    favoriteWebSocketDataSource.send(event.foodId)
                }
            }
        }
    }
}