package com.adedom.welcome.presentation.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.adedom.welcome.presentation.event.WelcomeUiEvent
import com.adedom.welcome.presentation.view_model.WelcomeViewModel
import org.kodein.di.compose.rememberInstance

@Composable
fun WelcomeScreen(
    onEvent: (WelcomeUiEvent) -> Unit,
) {
    val viewModel: WelcomeViewModel by rememberInstance()

    LaunchedEffect(viewModel.uiEvent) {
        viewModel.uiEvent.collect { uiEvent ->
            onEvent(uiEvent)
        }
    }

    WelcomeContent(
        onClickLogin = viewModel::onLoginEvent,
        onClickRegister = viewModel::onRegisterEvent,
        onClickSkip = viewModel::onSkipEvent,
    )
}