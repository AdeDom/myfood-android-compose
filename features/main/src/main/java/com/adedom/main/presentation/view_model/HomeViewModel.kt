package com.adedom.main.presentation.view_model

import com.adedom.core.domain.models.FoodModel
import com.adedom.core.utils.Resource
import com.adedom.main.domain.models.CategoryModel
import com.adedom.main.domain.use_cases.GetFoodListByCategoryIdUseCase
import com.adedom.main.domain.use_cases.HomeContentUseCase
import com.adedom.main.domain.use_cases.LogoutUseCase
import com.adedom.myfood.data.models.base.BaseError
import com.adedom.ui_components.base.BaseViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class HomeUiState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val error: BaseError? = null,
    val categories: List<CategoryModel> = emptyList(),
    val categoryName: String = "",
    val foods: List<FoodModel> = emptyList(),
    val categoryIdClick: Long? = null,
    val isLogoutDialog: Boolean = false,
)

sealed interface HomeUiEvent {
    object Logout : HomeUiEvent
    object SearchFood : HomeUiEvent
    data class FoodDetail(val foodId: Long) : HomeUiEvent
    object OnBackAlert : HomeUiEvent
    object OnBackPressed : HomeUiEvent
}

sealed interface HomeUiAction {
    data class CategoryClick(val categoryId: Long) : HomeUiAction
    data class FoodClick(val foodId: Long) : HomeUiAction
    object NavSearch : HomeUiAction
    object ErrorDismiss : HomeUiAction
    object Refreshing : HomeUiAction
    object BackHandler : HomeUiAction
    data class Logout(val isLogoutDialog: Boolean) : HomeUiAction
}

class HomeViewModel(
    private val homeContentUseCase: HomeContentUseCase,
    private val getFoodListByCategoryIdUseCase: GetFoodListByCategoryIdUseCase,
    private val logoutUseCase: LogoutUseCase,
) : BaseViewModel<HomeUiState, HomeUiEvent, HomeUiAction>(HomeUiState()) {

    private var isBackPressed = false
    private var backPressedJob: Job? = null

    init {
        launch {
            callHomeContent(isLoading = true)
        }
    }

    private suspend fun callHomeContent(
        isLoading: Boolean = false,
        isRefreshing: Boolean = false,
    ) {
        setState {
            copy(
                isLoading = isLoading,
                isRefreshing = isRefreshing,
                error = null,
            )
        }

        val resource = homeContentUseCase()
        when (resource) {
            is Resource.Success -> {
                val (categoryName, foods) = getFoodListByCategoryIdUseCase(
                    categoryId = uiState.categoryIdClick ?: CATEGORY_RECOMMEND,
                )
                setState {
                    copy(
                        isLoading = false,
                        isRefreshing = false,
                        categories = resource.data,
                        categoryName = categoryName,
                        foods = foods,
                        categoryIdClick = uiState.categoryIdClick ?: CATEGORY_RECOMMEND
                    )
                }
            }
            is Resource.Error -> {
                setState {
                    copy(
                        isLoading = false,
                        isRefreshing = false,
                        error = resource.error,
                    )
                }
            }
        }
    }

    fun onLogoutEvent() {
        GlobalScope.launch {
            setEvent(HomeUiEvent.Logout)
            logoutUseCase()
        }
    }

    override fun dispatch(action: HomeUiAction) {
        launch {
            when (action) {
                is HomeUiAction.CategoryClick -> {
                    val (categoryName, foods) = getFoodListByCategoryIdUseCase(action.categoryId)
                    setState {
                        copy(
                            categoryName = categoryName,
                            foods = foods,
                            categoryIdClick = action.categoryId,
                        )
                    }
                }
                is HomeUiAction.FoodClick -> {
                    setEvent(HomeUiEvent.FoodDetail(action.foodId))
                }
                HomeUiAction.NavSearch -> {
                    setEvent(HomeUiEvent.SearchFood)
                }
                HomeUiAction.ErrorDismiss -> {
                    callHomeContent(isLoading = true)
                }
                HomeUiAction.Refreshing -> {
                    callHomeContent(isRefreshing = true)
                }
                HomeUiAction.BackHandler -> {
                    backPressedJob?.cancel()
                    backPressedJob = launch {
                        val event = if (isBackPressed) {
                            HomeUiEvent.OnBackPressed
                        } else {
                            HomeUiEvent.OnBackAlert
                        }
                        setEvent(event)

                        isBackPressed = true
                        delay(2_000)
                        isBackPressed = false
                    }
                }
                is HomeUiAction.Logout -> {
                    setState { copy(isLogoutDialog = action.isLogoutDialog) }
                }
            }
        }
    }

    companion object {
        const val CATEGORY_RECOMMEND = 1L
    }
}