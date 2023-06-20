package com.adedom.welcome.presentation.view_model

import androidx.lifecycle.viewModelScope
import com.adedom.ui_components.base.BaseViewModel
import com.adedom.welcome.domain.use_cases.WelcomeGuestRoleUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

object WelcomeUiState

sealed interface WelcomeUiEvent {
    object NavSkip : WelcomeUiEvent
    data class OnChangeLanguage(
        val value: String,
    ) : WelcomeUiEvent
}

class WelcomeViewModel(
    private val welcomeGuestRoleUseCase: WelcomeGuestRoleUseCase,
) : BaseViewModel<WelcomeUiEvent, WelcomeUiState>(WelcomeUiState) {

    private val _nav = Channel<Unit>()
    val nav: Flow<Unit> = _nav.receiveAsFlow()

    private val _onChangeEnLanguage = Channel<String>()
    val onChangeEnLanguage: Flow<String> = _onChangeEnLanguage.receiveAsFlow()

    override fun onEvent(event: WelcomeUiEvent) {
        viewModelScope.launch {
            when (event) {
                WelcomeUiEvent.NavSkip -> {
                    _nav.send(welcomeGuestRoleUseCase())
                }

                is WelcomeUiEvent.OnChangeLanguage -> {
                    _onChangeEnLanguage.send(event.value)
                }
            }
        }
    }
}