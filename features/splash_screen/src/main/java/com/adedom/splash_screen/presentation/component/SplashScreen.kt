package com.adedom.splash_screen.presentation.component

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adedom.splash_screen.R
import com.adedom.splash_screen.presentation.view_model.SplashScreenViewModel
import com.adedom.ui.components.components.AppImage
import com.adedom.ui.components.theme.MyFoodTheme
import com.adedom.ui.components.R as res

@Composable
fun SplashScreen(
    viewModel: SplashScreenViewModel,
    openMainPage: () -> Unit,
    openWelcomePage: () -> Unit,
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.nav.collect { isAuth ->
            if (isAuth) {
                openMainPage()
            } else {
                openWelcomePage()
            }
        }
    }

    SplashScreenContent()
}

@Composable
fun SplashScreenContent() {
    val scale = remember { Animatable(0f) }
    val overshootInterpolator = remember { OvershootInterpolator(5f) }

    LaunchedEffect(key1 = Unit) {
        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 2_000,
                easing = overshootInterpolator::getInterpolation,
            ),
        )
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        AppImage(
            image = painterResource(id = R.drawable.bg),
            contentDescription = stringResource(id = res.string.str_background_splash_screen),
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
        )

        AppImage(
            image = painterResource(id = res.drawable.logo_black),
            contentDescription = stringResource(id = res.string.cd_logo_app),
            modifier = Modifier
                .width(200.dp)
                .scale(scale.value),
        )
    }
}

@Preview(
    name = "Splash screen content",
    group = "Feature - Splash screen",
    showBackground = true,
)
@Composable
fun SplashScreenPreview() {
    MyFoodTheme {
        SplashScreenContent()
    }
}