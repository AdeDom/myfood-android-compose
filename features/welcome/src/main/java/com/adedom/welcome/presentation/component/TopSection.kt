package com.adedom.welcome.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adedom.ui_components.components.LogoApp
import com.adedom.ui_components.theme.MyFoodTheme
import com.adedom.welcome.R

@Composable
fun TopSection(
    modifier: Modifier = Modifier,
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp

    Card(
        elevation = 32.dp,
        shape = RoundedCornerShape(
            bottomStart = 32.dp,
            bottomEnd = 32.dp,
        ),
        modifier = modifier
            .fillMaxWidth()
            .height((screenHeight / 2) - 64.dp)
            .testTag("Card welcome top"),
    ) {
        Image(
            painter = painterResource(id = R.drawable.orange_top_shape),
            contentDescription = "Background logo app",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
        )

        LogoApp(
            modifier = Modifier
                .fillMaxSize()
                .testTag("Logo app"),
        )
    }
}

@Composable
@Preview(showBackground = true)
fun TopSectionPreview() {
    MyFoodTheme {
        TopSection()
    }
}