package com.adedom.welcome.presentation.view_model

import androidx.lifecycle.viewModelScope
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
) : BaseViewModel<WelcomeUiState, WelcomeUiEvent>(WelcomeUiState) {

    fun dispatch(action: WelcomeUiAction) {
        viewModelScope.launch {
            when (action) {
                WelcomeUiAction.NavLogin -> {
                    _uiEvent.emit(WelcomeUiEvent.NavLogin)
                }
                WelcomeUiAction.NavRegister -> {
                    _uiEvent.emit(WelcomeUiEvent.NavRegister)
                }
                WelcomeUiAction.NavSkip -> {
                    _uiEvent.emit(WelcomeUiEvent.NavSkip(welcomeGuestRoleUseCase()))
                }
            }
        }
    }
}