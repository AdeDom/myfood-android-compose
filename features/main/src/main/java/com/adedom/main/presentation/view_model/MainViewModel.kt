package com.adedom.main.presentation.view_model

import androidx.lifecycle.viewModelScope
import com.adedom.core.utils.Resource
import com.adedom.main.domain.use_cases.GetFoodListByCategoryIdUseCase
import com.adedom.main.domain.use_cases.LogoutUseCase
import com.adedom.main.domain.use_cases.MainContentUseCase
import com.adedom.main.presentation.event.MainUiEvent
import com.adedom.main.presentation.state.MainUiState
import com.adedom.ui_components.base.BaseViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel(
    private val mainContentUseCase: MainContentUseCase,
    private val getFoodListByCategoryIdUseCase: GetFoodListByCategoryIdUseCase,
    private val logoutUseCase: LogoutUseCase,
) : BaseViewModel<MainUiState, MainUiEvent>(MainUiState()) {

    private var isBackPressed = false
    private var backPressedJob: Job? = null

    init {
        callMainContent()
    }

    fun callMainContent() {
        viewModelScope.launch {
            uiState = uiState.copy(
                isLoading = true,
                error = null,
            )

            val resource = mainContentUseCase()
            uiState = when (resource) {
                is Resource.Success -> {
                    uiState.copy(
                        isLoading = false,
                        categories = resource.data.categories,
                        foods = resource.data.foods,
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

    fun getFoodListByCategoryId(categoryId: Long) {
        viewModelScope.launch {
            val (categoryName, foods) = getFoodListByCategoryIdUseCase(categoryId)
            uiState = uiState.copy(
                categoryName = categoryName,
                foods = foods,
            )
        }
    }

    fun onLogoutEvent() {
        GlobalScope.launch {
            val event = MainUiEvent.Logout
            _uiEvent.emit(event)
            logoutUseCase()
        }
    }

    fun onSearchFoodEvent() {
        viewModelScope.launch {
            val event = MainUiEvent.SearchFood
            _uiEvent.emit(event)
        }
    }

    fun onFoodDetailEvent(foodId: Long) {
        viewModelScope.launch {
            val event = MainUiEvent.FoodDetail(foodId)
            _uiEvent.emit(event)
        }
    }

    fun onBackHandler() {
        backPressedJob?.cancel()
        backPressedJob = viewModelScope.launch {
            val event = if (isBackPressed) {
                MainUiEvent.OnBackPressed
            } else {
                MainUiEvent.OnBackAlert
            }
            _uiEvent.emit(event)

            isBackPressed = true
            delay(2_000)
            isBackPressed = false
        }
    }
}