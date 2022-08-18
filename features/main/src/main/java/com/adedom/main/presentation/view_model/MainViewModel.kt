package com.adedom.main.presentation.view_model

import com.adedom.core.utils.Resource
import com.adedom.main.domain.models.CategoryModel
import com.adedom.main.domain.models.FoodModel
import com.adedom.main.domain.use_cases.LogoutUseCase
import com.adedom.main.domain.use_cases.MainContentUseCase
import com.adedom.main.presentation.event.MainUiEvent
import com.adedom.main.presentation.state.MainUiState
import com.adedom.ui_components.base.BaseViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainViewModel(
    private val mainContentUseCase: MainContentUseCase,
    private val logoutUseCase: LogoutUseCase,
) : BaseViewModel<MainUiState, MainUiEvent>(MainUiState()) {

    fun setInitState(categoryList: List<CategoryModel>, foodList: List<FoodModel>) {
        uiState = uiState.copy(
            categoryList = categoryList,
            foodList = foodList,
        )
    }

    fun callMainContent() {
        launch {
            uiState = uiState.copy(
                isLoading = true,
                error = null,
            )

            val resource = mainContentUseCase()
            uiState = when (resource) {
                is Resource.Success -> {
                    val event = MainUiEvent.SaveState(
                        categoryList = resource.data.categoryList,
                        foodList = resource.data.foodList,
                    )
                    _uiEvent.emit(event)
                    uiState.copy(
                        isLoading = false,
                        categoryList = resource.data.categoryList,
                        foodList = resource.data.foodList,
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

    fun callLogout() {
        GlobalScope.launch {
            logoutUseCase()
        }
    }

    fun onLogoutEvent() {
        launch {
            val event = MainUiEvent.Logout
            _uiEvent.emit(event)
        }
    }

    fun onFoodDetailEvent(foodId: Int) {
        launch {
            val event = MainUiEvent.FoodDetail(foodId)
            _uiEvent.emit(event)
        }
    }
}