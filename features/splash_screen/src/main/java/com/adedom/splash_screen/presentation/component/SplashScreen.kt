package com.adedom.splash_screen.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.adedom.splash_screen.R
import com.adedom.splash_screen.presentation.event.SplashScreenUiEvent
import com.adedom.splash_screen.presentation.view_model.SplashScreenViewModel
import com.adedom.ui_components.components.LogoApp
import org.kodein.di.compose.rememberInstance

@Composable
fun SplashScreen(onNavigate: (SplashScreenUiEvent) -> Unit) {
    val viewModel: SplashScreenViewModel by rememberInstance()

    LaunchedEffect(viewModel.uiEvent) {
        viewModel.uiEvent.collect { uiEvent ->
            onNavigate(uiEvent)
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Image(
            painter = painterResource(id = R.drawable.bg),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
        )

        LogoApp()
    }
}