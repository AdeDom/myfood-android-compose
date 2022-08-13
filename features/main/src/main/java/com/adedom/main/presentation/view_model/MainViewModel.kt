package com.adedom.main.presentation.view_model

import com.adedom.core.base.BaseViewModel
import com.adedom.main.domain.use_cases.GetUserProfileUseCase
import com.adedom.main.domain.use_cases.LogoutUseCase
import com.adedom.main.domain.use_cases.MainPageUseCase
import com.adedom.main.presentation.event.MainUiEvent
import com.adedom.main.presentation.state.MainUiState
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val mainPageUseCase: MainPageUseCase,
    private val logoutUseCase: LogoutUseCase,
) : BaseViewModel<MainUiState, MainUiEvent>(MainUiState.Initial) {

    init {
        getUserProfile()
    }

    private fun getUserProfile() {
        launch {
            getUserProfileUseCase().collect { userProfile ->
                _uiState.update {
                    MainUiState.ShowUserProfile(userProfile)
                }
            }
        }
    }

    fun callApiService() {
        launch {
            try {
                mainPageUseCase()
            } catch (ex: Throwable) {
                ex.printStackTrace()
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
}