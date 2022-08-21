package com.adedom.ui_components.components

import androidx.annotation.DrawableRes
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource

@Composable
fun AppIcon(
    image: ImageVector,
    modifier: Modifier = Modifier,
    color: Color = Color.Black,
) {
    Icon(
        imageVector = image,
        contentDescription = null,
        tint = color,
        modifier = modifier,
    )
}

@Composable
fun AppIcon(
    @DrawableRes image: Int,
    modifier: Modifier = Modifier,
) {
    Icon(
        painter = painterResource(id = image),
        contentDescription = null,
        modifier = modifier,
    )
}