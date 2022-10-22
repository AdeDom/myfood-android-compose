package com.adedom.ui_components.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adedom.ui_components.R
import com.adedom.ui_components.theme.MyFoodTheme

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
        AppTitleText(
            text = "Empty data...",
            color = Color.Gray,
        )
    }
}

@Preview
@Composable
fun AppEmptyDataPreview() {
    MyFoodTheme {
        AppEmptyData()
    }
}