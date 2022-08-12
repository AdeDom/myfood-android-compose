package com.adedom.splash_screen.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.adedom.splash_screen.R
import com.adedom.splash_screen.presentation.event.SplashScreenUiEvent
import com.adedom.splash_screen.presentation.view_model.SplashScreenViewModel
import org.kodein.di.compose.rememberInstance

@Composable
fun SplashScreen(onNavigate: (SplashScreenUiEvent) -> Unit) {
    val viewModel: SplashScreenViewModel by rememberInstance()

    LaunchedEffect(key1 = true) {
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

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.align(Alignment.Center),
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_black),
                contentDescription = null,
                modifier = Modifier.width(200.dp),
            )
            Image(
                painter = painterResource(id = R.drawable.logo_food_delivery),
                contentDescription = null,
                modifier = Modifier.width(120.dp),
            )
        }
    }
}