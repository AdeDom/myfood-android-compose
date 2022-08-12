package com.adedom.welcome.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.adedom.welcome.R

@Composable
fun WelcomeScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        TopSection()
    }
}

@Composable
private fun TopSection() {
    val configuration = LocalConfiguration.current

    val screenHeight = configuration.screenHeightDp.dp

    Card(
        elevation = 32.dp,
        shape = RoundedCornerShape(
            bottomStart = 32.dp,
            bottomEnd = 32.dp,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height((screenHeight / 2) - 64.dp),
    ) {
        Image(
            painter = painterResource(id = R.drawable.orange_top_shape),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
        )

        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.align(Alignment.Center),
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo_black),
                    contentDescription = null,
                    modifier = Modifier.width(200.dp),
                )
                Image(
                    painter = painterResource(id = R.drawable.logo_food_delivery),
                    contentDescription = null,
                    modifier = Modifier.width(120.dp),
                )
            }
        }
    }
}