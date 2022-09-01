package com.adedom.ui_components.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.adedom.ui_components.R

@Composable
fun AppEmptyData(
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_playlist_remove_gray),
            contentDescription = null,
            modifier = Modifier.size(
                width = 100.dp,
                height = 100.dp,
            ),
        )
        AppText(
            text = "Empty data...",
            color = Color(0xFF888888),
            fontSize = 24.sp,
        )
    }
}