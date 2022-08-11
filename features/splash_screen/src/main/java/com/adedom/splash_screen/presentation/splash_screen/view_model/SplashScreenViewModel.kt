package com.adedom.splash_screen.presentation.splash_screen.view_model

import com.adedom.core.base.BaseViewModel
import com.adedom.splash_screen.domain.use_cases.GetIsAuthUseCase
import com.adedom.splash_screen.presentation.splash_screen.event.SplashScreenUiEvent
import com.adedom.splash_screen.presentation.splash_screen.state.SplashScreenUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.update

class SplashScreenViewModel(
    private val getIsAuthUseCase: GetIsAuthUseCase,
) : BaseViewModel<SplashScreenUiState, SplashScreenUiEvent>(SplashScreenUiState.Initial) {

    init {
        getIsAuth()
    }

    private fun getIsAuth() {
        launch {
            delay(2_000)
            val isAuth = getIsAuthUseCase()
            if (isAuth) {
                _uiState.update {
                    SplashScreenUiState.Authentication
                }
            } else {
                _uiState.update {
                    SplashScreenUiState.UnAuthentication
                }
            }
        }
    }
}