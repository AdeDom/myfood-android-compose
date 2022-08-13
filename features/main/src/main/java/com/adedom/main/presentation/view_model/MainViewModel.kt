package com.adedom.main.presentation.view_model

import com.adedom.core.base.BaseViewModel
import com.adedom.main.presentation.event.MainUiEvent
import com.adedom.main.presentation.state.MainUiState

class MainViewModel : BaseViewModel<MainUiState, MainUiEvent>(MainUiState.Initial) {

    init {
        getUserProfile()
    }

    private fun getUserProfile() {
//        launch {
//            getUserProfileUseCase().collect { userProfile ->
//                _uiState.update {
//                    MainUiState.ShowUserProfile(userProfile)
//                }
//            }
//        }
    }

    fun callApiService() {
//        launch {
//            try {
//                mainPageUseCase()
//            } catch (ex: Throwable) {
//                ex.printStackTrace()
//            }
//        }
    }

    fun callLogout() {
//        GlobalScope.launch {
//            logoutUseCase()
//        }
    }

    fun onLogoutEvent() {
        launch {
            val event = MainUiEvent.Logout
            _uiEvent.emit(event)
        }
    }
}