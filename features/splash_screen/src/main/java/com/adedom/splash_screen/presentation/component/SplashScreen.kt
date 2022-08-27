package com.adedom.splash_screen.presentation.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.adedom.splash_screen.R
import com.adedom.splash_screen.presentation.view_model.SplashScreenUiEvent
import com.adedom.splash_screen.presentation.view_model.SplashScreenViewModel
import com.adedom.ui_components.components.AppImage
import com.adedom.ui_components.components.LogoApp
import com.adedom.ui_components.theme.MyFoodTheme
import org.koin.androidx.compose.getViewModel

@Composable
fun SplashScreen(
    onEvent: (SplashScreenUiEvent) -> Unit,
) {
    val viewModel: SplashScreenViewModel = getViewModel()

    LaunchedEffect(viewModel.uiEvent) {
        viewModel.uiEvent.collect { uiEvent ->
            onEvent(uiEvent)
        }
    }

    SplashScreenContent()
}

@Composable
fun SplashScreenContent() {
    AppImage(
        image = R.drawable.bg,
        modifier = Modifier.fillMaxSize(),
    )

    LogoApp(
        modifier = Modifier.fillMaxSize(),
    )
}

@Composable
@Preview(showBackground = true)
fun SplashScreenPreview() {
    MyFoodTheme {
        SplashScreenContent()
    }
}