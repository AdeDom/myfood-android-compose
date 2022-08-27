package com.adedom.welcome.presentation.view_model

import com.adedom.ui_components.base.BaseViewModel
import com.adedom.welcome.domain.use_cases.WelcomeGuestRoleUseCase
import kotlinx.coroutines.launch

object WelcomeUiState

sealed interface WelcomeUiEvent {
    object NavLogin : WelcomeUiEvent
    object NavRegister : WelcomeUiEvent
    data class NavSkip(val value: Unit) : WelcomeUiEvent
}

sealed interface WelcomeUiAction {
    object NavLogin : WelcomeUiAction
    object NavRegister : WelcomeUiAction
    object NavSkip : WelcomeUiAction
}

class WelcomeViewModel(
    private val welcomeGuestRoleUseCase: WelcomeGuestRoleUseCase,
) : BaseViewModel<WelcomeUiState, WelcomeUiEvent, WelcomeUiAction>(WelcomeUiState) {

    override fun dispatch(action: WelcomeUiAction) {
        launch {
            when (action) {
                WelcomeUiAction.NavLogin -> {
                    setEvent(WelcomeUiEvent.NavLogin)
                }
                WelcomeUiAction.NavRegister -> {
                    setEvent(WelcomeUiEvent.NavRegister)
                }
                WelcomeUiAction.NavSkip -> {
                    setEvent(WelcomeUiEvent.NavSkip(welcomeGuestRoleUseCase()))
                }
            }
        }
    }
}