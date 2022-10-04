package com.adedom.main.presentation.view_model

import com.adedom.core.utils.ApiServiceException
import com.adedom.core.utils.RefreshTokenExpiredException
import com.adedom.domain.use_cases.GetIsActiveFavoriteWebSocketUseCase
import com.adedom.domain.use_cases.InitFavoriteWebSocketUseCase
import com.adedom.main.domain.models.CategoryModel
import com.adedom.main.domain.use_cases.*
import com.adedom.ui_components.base.BaseViewModel
import com.adedom.ui_components.domain.models.FoodModel
import com.myfood.server.data.models.base.BaseError
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

data class HomeUiState(
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
        object Loading : Dialog
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
    object Logout : HomeUiEvent
    object HideDialog : HomeUiEvent
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
    private val initFavoriteWebSocketUseCase: InitFavoriteWebSocketUseCase,
    private val getIsActiveFavoriteWebSocketUseCase: GetIsActiveFavoriteWebSocketUseCase,
) : BaseViewModel<HomeUiEvent, HomeUiState>(HomeUiState()) {

    private var isBackPressed = false
    private var backPressedJob: Job? = null

    private val _channel = Channel<HomeChannel>()
    val channel: Flow<HomeChannel> = _channel.receiveAsFlow()

    init {
        setupHome()
        setupMyFavorite()
    }

    private fun setupHome() {
        launch {
            coState {
                copy(
                    isExitAuth = getIsAuthRoleUseCase(),
                    imageProfile = getImageProfileUseCase(),
                )
            }
            callHomeContent(dialog = HomeUiState.Dialog.Loading)
        }
    }

    private fun setupMyFavorite() {
        launch {
            initFavoriteWebSocketUseCase()
            while (true) {
                if (!getIsActiveFavoriteWebSocketUseCase()) {
                    initFavoriteWebSocketUseCase()
                }
                delay(2_000)
            }
        }
    }

    private suspend fun callHomeContent(
        dialog: HomeUiState.Dialog? = null,
        isRefreshing: Boolean = false,
    ) {
        setState {
            copy(
                dialog = dialog,
                isRefreshing = isRefreshing,
            )
        }

        try {
            val categories = homeContentUseCase()
            val (categoryName, foods) = getFoodListByCategoryIdUseCase(
                categoryId = uiState.categoryIdClick ?: CATEGORY_RECOMMEND,
            )
            setState {
                copy(
                    dialog = null,
                    isRefreshing = false,
                    categories = categories,
                    categoryName = categoryName,
                    foods = foods,
                    categoryIdClick = uiState.categoryIdClick ?: CATEGORY_RECOMMEND,
                )
            }
        } catch (exception: ApiServiceException) {
            setState {
                copy(
                    isRefreshing = false,
                    dialog = HomeUiState.Dialog.Error(exception.toBaseError()),
                )
            }
        }
    }

    fun onLogoutEvent() {
        GlobalScope.launch {
            try {
                _channel.send(HomeChannel.Logout)
                logoutUseCase()
            } catch (exception: ApiServiceException) {
                exception.printStackTrace()
            } catch (exception: RefreshTokenExpiredException) {
                exception.printStackTrace()
            }
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
                    callHomeContent(dialog = HomeUiState.Dialog.Loading)
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
                HomeUiEvent.Logout -> {
                    setState { copy(dialog = HomeUiState.Dialog.Logout) }
                }
                HomeUiEvent.HideDialog -> {
                    setState { copy(dialog = null) }
                }
            }
        }
    }

    companion object {
        const val CATEGORY_RECOMMEND = 1L
    }
}