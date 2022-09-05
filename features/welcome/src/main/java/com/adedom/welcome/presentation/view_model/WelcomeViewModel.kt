package com.adedom.welcome.presentation.view_model

import com.adedom.ui_components.base.BaseMvi
import com.adedom.welcome.domain.use_cases.WelcomeGuestRoleUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

object WelcomeUiState

sealed interface WelcomeUiAction {
    object NavSkip : WelcomeUiAction
}

class WelcomeViewModel(
    private val welcomeGuestRoleUseCase: WelcomeGuestRoleUseCase,
) : BaseMvi<WelcomeUiState, WelcomeUiAction>(WelcomeUiState) {

    private val _nav = Channel<Unit>()
    val nav: Flow<Unit> = _nav.receiveAsFlow()

    override fun dispatch(action: WelcomeUiAction) {
        launch {
            when (action) {
                WelcomeUiAction.NavSkip -> {
                    _nav.send(welcomeGuestRoleUseCase())
                }
            }
        }
    }
}