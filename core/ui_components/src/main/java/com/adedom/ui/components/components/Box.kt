package com.adedom.ui.components.components

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
fun AnimatedBrushBox(
    modifier: Modifier = Modifier,
    initialColor: Color = Color.Black,
    startY: Float = 100f,
    durationMillis: Int = 1000
) {
    val infiniteTransition = rememberInfiniteTransition()
    val imageBrushColor by infiniteTransition.animateColor(
        initialValue = initialColor,
        targetValue = Color.Transparent,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        modifier = modifier.background(
            Brush.verticalGradient(
                colors = listOf(
                    Color.Transparent,
                    imageBrushColor
                ),
                startY = startY
            )
        )
    )
}
