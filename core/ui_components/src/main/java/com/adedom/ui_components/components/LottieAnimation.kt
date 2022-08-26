package com.adedom.ui_components.components

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.adedom.ui_components.R
import com.airbnb.lottie.compose.*

@Composable
fun AppLoadingLottieAnimation(
    modifier: Modifier = Modifier,
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading))
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever,
    )
    Box(
        modifier = modifier,
    ) {
        LottieAnimation(
            composition = composition,
            progress = { progress },
        )
    }
}