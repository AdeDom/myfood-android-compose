package com.adedom.ui_components.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.adedom.ui_components.R

@Composable
fun LogoApp() {
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.align(Alignment.Center),
        ) {
            AppImage(
                image = R.drawable.logo_black,
                modifier = Modifier.width(200.dp),
            )
            AppImage(
                image = R.drawable.logo_food_delivery,
                modifier = Modifier.width(120.dp),
            )
        }
    }
}