package com.adedom.splash_screen.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.adedom.splash_screen.R
import com.adedom.splash_screen.presentation.event.SplashScreenUiEvent
import com.adedom.splash_screen.presentation.view_model.SplashScreenViewModel
import com.adedom.ui_components.components.AppImage
import com.adedom.ui_components.components.LogoApp
import org.kodein.di.compose.rememberInstance

@Composable
fun SplashScreen(
    onEvent: (SplashScreenUiEvent) -> Unit,
) {
    val viewModel: SplashScreenViewModel by rememberInstance()

    LaunchedEffect(viewModel.uiEvent) {
        viewModel.uiEvent.collect { uiEvent ->
            onEvent(uiEvent)
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        AppImage(
            image = R.drawable.bg,
            modifier = Modifier.fillMaxSize(),
        )

        LogoApp()
    }
}