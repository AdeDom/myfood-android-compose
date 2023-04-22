package com.adedom.welcome.presentation.view_model

import com.adedom.ui_components.base.BaseViewModel
import com.adedom.welcome.domain.use_cases.WelcomeGuestRoleUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

object WelcomeUiState

sealed interface WelcomeUiEvent {
    object NavSkip : WelcomeUiEvent
}

class WelcomeViewModel(
    private val welcomeGuestRoleUseCase: WelcomeGuestRoleUseCase,
) : BaseViewModel<WelcomeUiEvent, WelcomeUiState>(WelcomeUiState) {

    private val _nav = Channel<Unit>()
    val nav: Flow<Unit> = _nav.receiveAsFlow()

    override fun onEvent(event: WelcomeUiEvent) {
        launch {
            when (event) {
                WelcomeUiEvent.NavSkip -> {
                    _nav.send(welcomeGuestRoleUseCase())
                }
            }
        }
    }
}