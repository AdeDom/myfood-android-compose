package com.adedom.splash_screen.presentation.view_model

import androidx.lifecycle.viewModelScope
import com.adedom.splash_screen.domain.use_cases.GetIsAuthUseCase
import com.adedom.ui_components.base.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object SplashScreenUiState

sealed interface SplashScreenUiEvent {
    object NavMain : SplashScreenUiEvent
    object NavWelcome : SplashScreenUiEvent
}

class SplashScreenViewModel(
    private val getIsAuthUseCase: GetIsAuthUseCase,
) : BaseViewModel<SplashScreenUiState, SplashScreenUiEvent>(SplashScreenUiState) {

    init {
        viewModelScope.launch {
            delay(2_000)
            val isAuth = getIsAuthUseCase()
            if (isAuth) {
                _uiEvent.emit(SplashScreenUiEvent.NavMain)
            } else {
                _uiEvent.emit(SplashScreenUiEvent.NavWelcome)
            }
        }
    }
}