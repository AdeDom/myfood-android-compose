package com.adedom.ui_components.components

import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun AppIcon(
    image: ImageVector,
    modifier: Modifier = Modifier,
    color: Color = Color.Black,
    contentDescription: String,
) {
    Icon(
        imageVector = image,
        contentDescription = contentDescription,
        tint = color,
        modifier = modifier,
    )
}

@Composable
fun AppIcon(
    image: Painter,
    modifier: Modifier = Modifier,
    contentDescription: String,
) {
    Icon(
        painter = image,
        contentDescription = contentDescription,
        modifier = modifier,
    )
}