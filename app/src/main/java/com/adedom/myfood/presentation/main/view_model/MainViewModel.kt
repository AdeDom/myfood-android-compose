package com.adedom.myfood.presentation.main.view_model

import com.adedom.domain.use_cases.logout.LogoutUseCase
import com.adedom.domain.use_cases.main.MainPageUseCase
import com.adedom.domain.use_cases.user_profile.GetUserProfileUseCase
import com.adedom.myfood.base.BaseViewModel
import com.adedom.myfood.presentation.main.event.MainUiEvent
import com.adedom.myfood.presentation.main.state.MainUiState
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