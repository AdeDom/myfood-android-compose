package com.adedom.ui.components.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import com.adedom.ui.components.R

@Composable
fun AppImage(
    image: Painter,
    modifier: Modifier = Modifier,
    contentDescription: String,
    contentScale: ContentScale = ContentScale.Crop
) {
    Image(
        painter = image,
        contentDescription = contentDescription,
        contentScale = contentScale,
        modifier = modifier
    )
}

@Composable
fun AppImage(
    image: String?,
    modifier: Modifier = Modifier,
    contentDescription: String,
    contentScale: ContentScale = ContentScale.Crop
) {
    if (image.isNullOrEmpty()) {
        Image(
            painter = painterResource(id = R.drawable.logo_black),
            contentDescription = contentDescription,
            contentScale = contentScale,
            modifier = modifier
        )
    } else {
        AsyncImage(
            model = image,
            contentDescription = contentDescription,
            contentScale = contentScale,
            placeholder = painterResource(id = R.drawable.logo_black),
            modifier = modifier
        )
    }
}
