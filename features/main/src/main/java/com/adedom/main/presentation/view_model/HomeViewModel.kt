package com.adedom.main.presentation.view_model

import com.adedom.core.domain.models.FoodModel
import com.adedom.core.utils.Resource
import com.adedom.main.domain.models.CategoryModel
import com.adedom.main.domain.use_cases.*
import com.adedom.myfood.data.models.base.BaseError
import com.adedom.ui_components.base.BaseViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

data class HomeUiState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val imageProfile: String? = null,
    val categories: List<CategoryModel> = emptyList(),
    val categoryName: String = "",
    val foods: List<FoodModel> = emptyList(),
    val categoryIdClick: Long? = null,
    val isExitAuth: Boolean = false,
    val dialog: Dialog? = null,
) {
    sealed interface Dialog {
        data class Error(val error: BaseError) : Dialog
        object Logout : Dialog
    }
}

sealed interface HomeUiEvent {
    data class CategoryClick(val categoryId: Long) : HomeUiEvent
    object NavLogout : HomeUiEvent
    object ErrorDismiss : HomeUiEvent
    object Refreshing : HomeUiEvent
    object BackHandler : HomeUiEvent
    data class Logout(val isLogoutDialog: Boolean) : HomeUiEvent
}

sealed interface HomeChannel {
    object Logout : HomeChannel
    object OnBackPressed : HomeChannel
    object OnBackAlert : HomeChannel
}

class HomeViewModel(
    private val homeContentUseCase: HomeContentUseCase,
    private val getImageProfileUseCase: GetImageProfileUseCase,
    private val getFoodListByCategoryIdUseCase: GetFoodListByCategoryIdUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val getIsAuthRoleUseCase: GetIsAuthRoleUseCase,
    private val saveUnAuthRoleUseCase: SaveUnAuthRoleUseCase,
) : BaseViewModel<HomeUiEvent, HomeUiState>(HomeUiState()) {

    private var isBackPressed = false
    private var backPressedJob: Job? = null

    private val _channel = Channel<HomeChannel>()
    val channel: Flow<HomeChannel> = _channel.receiveAsFlow()

    init {
        launch {
            coState {
                copy(
                    isExitAuth = getIsAuthRoleUseCase(),
                    imageProfile = getImageProfileUseCase(),
                )
            }
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
                dialog = null,
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
                        categoryIdClick = uiState.categoryIdClick ?: CATEGORY_RECOMMEND,
                    )
                }
            }
            is Resource.Error -> {
                setState {
                    copy(
                        isLoading = false,
                        isRefreshing = false,
                        dialog = HomeUiState.Dialog.Error(resource.error),
                    )
                }
            }
        }
    }

    fun onLogoutEvent() {
        GlobalScope.launch {
            _channel.send(HomeChannel.Logout)
            logoutUseCase()
        }
    }

    override fun dispatch(event: HomeUiEvent) {
        launch {
            when (event) {
                is HomeUiEvent.CategoryClick -> {
                    val (categoryName, foods) = getFoodListByCategoryIdUseCase(event.categoryId)
                    setState {
                        copy(
                            categoryName = categoryName,
                            foods = foods,
                            categoryIdClick = event.categoryId,
                        )
                    }
                }
                HomeUiEvent.NavLogout -> {
                    saveUnAuthRoleUseCase()
                    _channel.send(HomeChannel.Logout)
                }
                HomeUiEvent.ErrorDismiss -> {
                    callHomeContent(isLoading = true)
                }
                HomeUiEvent.Refreshing -> {
                    callHomeContent(isRefreshing = true)
                }
                HomeUiEvent.BackHandler -> {
                    backPressedJob?.cancel()
                    backPressedJob = launch {
                        if (isBackPressed) {
                            _channel.send(HomeChannel.OnBackPressed)
                        } else {
                            _channel.send(HomeChannel.OnBackAlert)
                        }

                        isBackPressed = true
                        delay(2_000)
                        isBackPressed = false
                    }
                }
                is HomeUiEvent.Logout -> {
                    setState { copy(dialog = HomeUiState.Dialog.Logout) }
                }
            }
        }
    }

    companion object {
        const val CATEGORY_RECOMMEND = 1L
    }
}