package com.adedom.ui_components.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.adedom.ui_components.R

@Composable
fun LogoApp(
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AppImage(
                image = painterResource(id = R.drawable.logo_black),
                modifier = Modifier.width(200.dp),
                contentDescription = stringResource(id = R.string.cd_logo_app),
            )
            AppImage(
                image = painterResource(id = R.drawable.logo_food_delivery),
                modifier = Modifier.width(120.dp),
                contentDescription = stringResource(id = R.string.cd_logo_app),
            )
        }
    }
}