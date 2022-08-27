package com.adedom.welcome.presentation.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.adedom.welcome.presentation.view_model.WelcomeUiEvent
import com.adedom.welcome.presentation.view_model.WelcomeViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun WelcomeScreen(
    onEvent: (WelcomeUiEvent) -> Unit,
) {
    val viewModel: WelcomeViewModel = getViewModel()

    LaunchedEffect(viewModel.uiEvent) {
        viewModel.uiEvent.collect { uiEvent ->
            onEvent(uiEvent)
        }
    }

    WelcomeContent(
        viewModel::dispatch,
    )
}