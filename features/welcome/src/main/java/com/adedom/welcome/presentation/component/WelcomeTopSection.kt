package com.adedom.welcome.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
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
import com.adedom.ui.components.components.AnimatedBrushBox
import com.adedom.ui.components.components.AppImage
import com.adedom.ui.components.theme.MyFoodTheme
import com.adedom.ui.components.R as res

@Composable
fun WelcomeTopSection(
    modifier: Modifier = Modifier,
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp

    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 32.dp),
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
                .background(MaterialTheme.colorScheme.primary)
                .semantics {
                    contentDescription = "Background logo app"
                },
        ) {
            AppImage(
                image = painterResource(id = res.drawable.logo_black),
                contentDescription = stringResource(id = res.string.cd_logo_app),
                modifier = Modifier.width(200.dp),
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .align(Alignment.BottomCenter),
            ) {
                AnimatedBrushBox(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@Preview(
    name = "Welcome top",
    group = "Feature - Welcome",
    showBackground = true,
)
@Composable
fun WelcomeTopSectionPreview() {
    MyFoodTheme {
        WelcomeTopSection()
    }
}