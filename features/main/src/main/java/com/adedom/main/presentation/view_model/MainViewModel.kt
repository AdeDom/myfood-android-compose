package com.adedom.main.presentation.view_model

import androidx.lifecycle.viewModelScope
import com.adedom.core.utils.Resource
import com.adedom.main.domain.models.MainContentModel
import com.adedom.main.domain.models.UserProfileModel
import com.adedom.main.domain.use_cases.GetUserProfileUseCase
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
    getUserProfileUseCase: GetUserProfileUseCase,
    private val mainContentUseCase: MainContentUseCase,
    private val logoutUseCase: LogoutUseCase,
) : BaseViewModel<MainUiState, MainUiEvent>(MainUiState()) {

    init {
        getUserProfileUseCase()
            .onEach { userProfile ->
                uiState = uiState.copy(userProfile = userProfile)
            }
            .launchIn(viewModelScope)
    }

    fun setInitState(userProfile: UserProfileModel, mainContent: MainContentModel) {
        uiState = uiState.copy(
            userProfile = userProfile,
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
                        userProfile = uiState.userProfile,
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