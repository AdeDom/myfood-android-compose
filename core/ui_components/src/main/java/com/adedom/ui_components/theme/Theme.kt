package com.adedom.ui_components.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = AppColor.Purple200,
    primaryVariant = AppColor.Purple700,
    secondary = AppColor.Teal200
)

private val LightColorPalette = lightColors(
    primary = AppColor.Purple500,
    primaryVariant = AppColor.Purple700,
    secondary = AppColor.Teal200
)

@Composable
fun MyFoodTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}