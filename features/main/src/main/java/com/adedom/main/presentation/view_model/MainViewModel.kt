package com.adedom.main.presentation.view_model

import androidx.lifecycle.viewModelScope
import com.adedom.core.utils.Resource
import com.adedom.main.domain.models.MainContentModel
import com.adedom.main.domain.use_cases.GetCategoryUseCase
import com.adedom.main.domain.use_cases.LogoutUseCase
import com.adedom.main.domain.use_cases.MainContentUseCase
import com.adedom.main.presentation.event.MainUiEvent
import com.adedom.main.presentation.state.MainUiState
import com.adedom.ui_components.base.BaseViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MainViewModel(
    getCategoryUseCase: GetCategoryUseCase,
    private val mainContentUseCase: MainContentUseCase,
    private val logoutUseCase: LogoutUseCase,
) : BaseViewModel<MainUiState, MainUiEvent>(MainUiState()) {

    init {
        getCategoryUseCase()
            .onEach { categoryList ->
                uiState = uiState.copy(categoryList = categoryList)
            }
            .launchIn(viewModelScope)
    }

    fun setInitState(mainContent: MainContentModel) {
        uiState = uiState.copy(
            mainContent = mainContent,
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
                        mainContent = resource.data,
                    )
                    _uiEvent.emit(event)
                    uiState.copy(
                        isLoading = false,
                        mainContent = resource.data,
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