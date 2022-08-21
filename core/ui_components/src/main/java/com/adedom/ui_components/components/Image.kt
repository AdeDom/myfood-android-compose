package com.adedom.ui_components.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import com.adedom.ui_components.R

@Composable
fun AppImage(
    modifier: Modifier = Modifier,
    @DrawableRes image: Int,
    contentScale: ContentScale = ContentScale.Crop,
) {
    Image(
        painter = painterResource(id = image),
        contentDescription = null,
        contentScale = contentScale,
        modifier = modifier,
    )
}

@Composable
fun AppImageNetwork(
    modifier: Modifier = Modifier,
    image: String,
    contentScale: ContentScale = ContentScale.Crop,
) {
    if (image.isNotEmpty()) {
        AsyncImage(
            model = image,
            contentDescription = null,
            contentScale = contentScale,
            modifier = modifier,
        )
    } else {
        AppImage(
            image = R.drawable.logo_black,
            contentScale = contentScale,
            modifier = modifier,
        )
    }
}