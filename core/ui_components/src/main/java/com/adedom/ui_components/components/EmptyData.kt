package com.adedom.ui_components.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
        AppImage(
            image = painterResource(id = R.drawable.ic_playlist_remove_gray),
            contentDescription = stringResource(id = R.string.str_empty_data),
            modifier = Modifier.size(100.dp),
        )
        AppSubTitleText(stringResource(id = R.string.str_empty_data))
    }
}

@Preview(
    name = "Empty data",
    group = "Component - EmptyData",
    showBackground = true,
)
@Composable
fun AppEmptyDataPreview() {
    MyFoodTheme {
        AppEmptyData()
    }
}