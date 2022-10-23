package com.adedom.welcome.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adedom.ui_components.components.AppImage
import com.adedom.ui_components.theme.MyFoodTheme
import com.adedom.ui_components.R as res

@Composable
fun WelcomeTopSection(
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
            .height((screenHeight / 2) - 64.dp),
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(550.dp)
                .background(MaterialTheme.colors.primary)
                .semantics {
                    contentDescription = "Background logo app"
                },
        ) {
            AppImage(
                image = painterResource(id = res.drawable.logo_black),
                contentDescription = stringResource(id = res.string.cd_logo_app),
                modifier = Modifier.width(200.dp),
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun WelcomeTopSectionPreview() {
    MyFoodTheme {
        WelcomeTopSection()
    }
}