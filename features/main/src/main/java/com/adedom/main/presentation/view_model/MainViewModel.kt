package com.adedom.main.presentation.view_model

import com.adedom.core.utils.Resource
import com.adedom.main.domain.models.CategoryModel
import com.adedom.main.domain.models.FoodModel
import com.adedom.main.domain.use_cases.GetFoodListByCategoryIdUseCase
import com.adedom.main.domain.use_cases.LogoutUseCase
import com.adedom.main.domain.use_cases.MainContentUseCase
import com.adedom.main.domain.use_cases.SearchFoodUseCase
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
    private val searchFoodUseCase: SearchFoodUseCase,
    private val logoutUseCase: LogoutUseCase,
) : BaseViewModel<MainUiState, MainUiEvent>(MainUiState()) {

    private var searchJob: Job? = null

    fun setInitState(
        categories: List<CategoryModel>,
        categoryName: String,
        foods: List<FoodModel>,
    ) {
        uiState = uiState.copy(
            categories = categories,
            categoryName = categoryName,
            foods = foods,
        )
    }

    fun setSearch(search: String) {
        uiState = uiState.copy(search = search)
    }

    fun onSearchFood(search: String) {
        searchJob?.cancel()
        searchJob = launch {
            delay(500)
            val foods = searchFoodUseCase(search)
            uiState = uiState.copy(foods = foods)
        }
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
                        categories = resource.data.categories,
                        foods = resource.data.foods,
                    )
                    _uiEvent.emit(event)

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
        launch {
            val (categoryName, foods) = getFoodListByCategoryIdUseCase(categoryId)
            uiState = uiState.copy(
                categoryName = categoryName,
                foods = foods,
            )

            val event = MainUiEvent.SaveState(
                categories = uiState.categories,
                categoryName = categoryName,
                foods = foods,
            )
            _uiEvent.emit(event)
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

    fun onFoodDetailEvent(foodId: Long) {
        launch {
            val event = MainUiEvent.FoodDetail(foodId)
            _uiEvent.emit(event)
        }
    }
}