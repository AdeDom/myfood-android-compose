package com.adedom.main.presentation.view_model

import androidx.lifecycle.viewModelScope
import com.adedom.core.domain.models.FoodModel
import com.adedom.core.utils.Resource
import com.adedom.main.domain.models.CategoryModel
import com.adedom.main.domain.use_cases.GetFoodListByCategoryIdUseCase
import com.adedom.main.domain.use_cases.LogoutUseCase
import com.adedom.main.domain.use_cases.MainContentUseCase
import com.adedom.myfood.data.models.base.BaseError
import com.adedom.ui_components.base.BaseViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class MainUiState(
    val isLoading: Boolean = false,
    val error: BaseError? = null,
    val categories: List<CategoryModel> = emptyList(),
    val categoryName: String = "",
    val foods: List<FoodModel> = emptyList(),
)

sealed interface MainUiEvent {
    object Logout : MainUiEvent
    object SearchFood : MainUiEvent
    data class FoodDetail(val foodId: Long) : MainUiEvent
    object OnBackAlert : MainUiEvent
    object OnBackPressed : MainUiEvent
}

sealed interface MainUiAction {
    data class CategoryClick(val categoryId: Long) : MainUiAction
    data class FoodClick(val foodId: Long) : MainUiAction
    object NavSearch : MainUiAction
    object ErrorDismiss : MainUiAction
    object BackHandler : MainUiAction
}

class MainViewModel(
    private val mainContentUseCase: MainContentUseCase,
    private val getFoodListByCategoryIdUseCase: GetFoodListByCategoryIdUseCase,
    private val logoutUseCase: LogoutUseCase,
) : BaseViewModel<MainUiState, MainUiEvent>(MainUiState()) {

    private var isBackPressed = false
    private var backPressedJob: Job? = null

    init {
        viewModelScope.launch {
            callMainContent()
        }
    }

    private suspend fun callMainContent() {
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

    fun onLogoutEvent() {
        GlobalScope.launch {
            _uiEvent.emit(MainUiEvent.Logout)
            logoutUseCase()
        }
    }

    fun dispatch(action: MainUiAction) {
        viewModelScope.launch {
            when (action) {
                is MainUiAction.CategoryClick -> {
                    val (categoryName, foods) = getFoodListByCategoryIdUseCase(action.categoryId)
                    uiState = uiState.copy(
                        categoryName = categoryName,
                        foods = foods,
                    )
                }
                is MainUiAction.FoodClick -> {
                    _uiEvent.emit(MainUiEvent.FoodDetail(action.foodId))
                }
                MainUiAction.NavSearch -> {
                    _uiEvent.emit(MainUiEvent.SearchFood)
                }
                MainUiAction.ErrorDismiss -> {
                    callMainContent()
                }
                MainUiAction.BackHandler -> {
                    backPressedJob?.cancel()
                    backPressedJob = launch {
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
        }
    }
}