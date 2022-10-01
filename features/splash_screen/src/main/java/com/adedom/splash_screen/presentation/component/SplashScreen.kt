package com.adedom.splash_screen.presentation.component

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import com.adedom.splash_screen.R
import com.adedom.splash_screen.presentation.view_model.SplashScreenViewModel
import com.adedom.ui_components.components.LogoApp
import com.adedom.ui_components.theme.MyFoodTheme

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

    Image(
        painter = painterResource(id = R.drawable.bg),
        contentDescription = "Background splash screen",
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize(),
    )

    LogoApp(
        modifier = Modifier
            .fillMaxSize()
            .scale(scale.value)
            .semantics {
                contentDescription = "Logo app"
            },
    )
}

@Composable
@Preview(showBackground = true)
fun SplashScreenPreview() {
    MyFoodTheme {
        SplashScreenContent()
    }
}