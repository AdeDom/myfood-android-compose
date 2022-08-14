package com.adedom.splash_screen.presentation.view_model

import com.adedom.splash_screen.domain.use_cases.GetIsAuthUseCase
import com.adedom.splash_screen.presentation.event.SplashScreenUiEvent
import com.adedom.splash_screen.presentation.state.SplashScreenUiState
import com.adedom.ui_components.base.BaseViewModel
import kotlinx.coroutines.delay

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
                _uiEvent.emit(SplashScreenUiEvent.Authentication)
            } else {
                _uiEvent.emit(SplashScreenUiEvent.UnAuthentication)
            }
        }
    }
}