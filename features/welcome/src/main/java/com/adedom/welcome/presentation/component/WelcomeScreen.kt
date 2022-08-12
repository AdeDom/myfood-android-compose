package com.adedom.welcome.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.adedom.ui_components.AppButton
import com.adedom.ui_components.AppText
import com.adedom.ui_components.LogoApp
import com.adedom.welcome.R

@Composable
fun WelcomeScreen(
    onNavigateLogin: () -> Unit,
    onNavigateRegister: () -> Unit,
    onNavigateSkip: () -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column {
            TopSection()
            Spacer(modifier = Modifier.height(64.dp))
            BottomSection(onNavigateLogin, onNavigateRegister, onNavigateSkip)
        }
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

        LogoApp()
    }
}

@Composable
private fun BottomSection(
    onNavigateLogin: () -> Unit,
    onNavigateRegister: () -> Unit,
    onNavigateSkip: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize(),
    ) {
        AppButton(
            text = "Login",
            backgroundColor = Color(0xFFFFD700),
            onClick = onNavigateLogin,
        )
        Spacer(modifier = Modifier.height(20.dp))
        AppButton(
            text = "Create an Account",
            backgroundColor = Color.White,
            onClick = onNavigateRegister,
        )
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier.clickable {
                onNavigateSkip()
            }
        ) {
            AppText(
                "Don\'t want login?",
                color = Color.Gray,
            )
            Spacer(modifier = Modifier.width(4.dp))
            AppText(
                "Skip",
                color = Color(0xFFFFD700),
                fontWeight = FontWeight.Bold,
            )
        }
    }
}