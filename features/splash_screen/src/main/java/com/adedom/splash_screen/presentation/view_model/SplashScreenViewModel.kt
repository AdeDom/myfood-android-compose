package com.adedom.splash_screen.presentation.view_model

import com.adedom.splash_screen.domain.use_cases.GetIsAuthUseCase
import com.adedom.ui_components.base.BaseViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

object SplashScreenUiState

sealed interface SplashScreenUiEvent

class SplashScreenViewModel(
    private val getIsAuthUseCase: GetIsAuthUseCase,
) : BaseViewModel<SplashScreenUiEvent, SplashScreenUiState>(SplashScreenUiState) {

    private val _nav = Channel<Boolean>()
    val nav: Flow<Boolean> = _nav.receiveAsFlow()

    init {
        launch {
            delay(2_000)
            val isAuth = getIsAuthUseCase()
            _nav.send(isAuth)
        }
    }

    override fun onEvent(event: SplashScreenUiEvent) {}
}