package com.adedom.myfood.presentation.splash_screen.view_model

import com.adedom.domain.use_cases.splash_screen.GetIsAuthUseCase
import com.adedom.myfood.base.BaseViewModel
import com.adedom.myfood.presentation.splash_screen.event.SplashScreenUiEvent
import com.adedom.myfood.presentation.splash_screen.state.SplashScreenUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.update

class SplashScreenViewModel(
    private val getIsAuthUseCase: GetIsAuthUseCase,
) : BaseViewModel<SplashScreenUiState, SplashScreenUiEvent>(SplashScreenUiState.Initial) {

    fun getIsAuth() {
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