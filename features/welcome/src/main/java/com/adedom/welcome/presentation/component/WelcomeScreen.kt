package com.adedom.welcome.presentation.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.adedom.welcome.presentation.view_model.WelcomeUiEvent
import com.adedom.welcome.presentation.view_model.WelcomeViewModel

@Composable
fun WelcomeScreen(
    viewModel: WelcomeViewModel,
    onEvent: (WelcomeUiEvent) -> Unit,
) {
    LaunchedEffect(viewModel.uiEvent) {
        viewModel.uiEvent.collect { uiEvent ->
            onEvent(uiEvent)
        }
    }

    WelcomeContent(
        viewModel::dispatch,
    )
}