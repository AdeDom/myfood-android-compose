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
    @DrawableRes image: Int,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.Crop,
) {
    Image(
        painter = painterResource(id = image),
        contentDescription = contentDescription,
        contentScale = contentScale,
        modifier = modifier,
    )
}

@Composable
fun AppImageNetwork(
    image: String?,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.Crop,
) {
    if (image.isNullOrEmpty()) {
        AppImage(
            image = R.drawable.logo_black,
            contentDescription = contentDescription,
            contentScale = contentScale,
            modifier = modifier,
        )
    } else {
        AsyncImage(
            model = image,
            contentDescription = contentDescription,
            contentScale = contentScale,
            placeholder = painterResource(id = R.drawable.logo_black),
            modifier = modifier,
        )
    }
}