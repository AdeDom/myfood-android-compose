package com.adedom.main.presentation.view_model

import com.adedom.core.utils.Resource
import com.adedom.main.domain.use_cases.GetUserProfileUseCase
import com.adedom.main.domain.use_cases.LogoutUseCase
import com.adedom.main.domain.use_cases.MainContentUseCase
import com.adedom.main.presentation.event.MainUiEvent
import com.adedom.main.presentation.state.MainUiState
import com.adedom.ui_components.base.BaseViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainViewModel(
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val mainContentUseCase: MainContentUseCase,
    private val logoutUseCase: LogoutUseCase,
) : BaseViewModel<MainUiState, MainUiEvent>(MainUiState()) {

    init {
        getUserProfile()
        callMainContent()
    }

    private fun getUserProfile() {
        launch {
            getUserProfileUseCase().collect { userProfile ->
                uiState = uiState.copy(userProfile = userProfile)
            }
        }
    }

    fun callMainContent() {
        launch {
            uiState = uiState.copy(isLoading = true)

            val resource = mainContentUseCase()
            uiState = when (resource) {
                is Resource.Success -> {
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