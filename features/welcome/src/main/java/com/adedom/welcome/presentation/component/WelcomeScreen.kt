package com.adedom.welcome.presentation.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.adedom.ui_components.components.AppBottomText
import com.adedom.ui_components.components.AppButton
import com.adedom.ui_components.components.AppText
import com.adedom.ui_components.components.LogoApp
import com.adedom.welcome.R
import com.adedom.welcome.presentation.event.WelcomeUiEvent
import com.adedom.welcome.presentation.view_model.WelcomeViewModel
import org.kodein.di.compose.rememberInstance

@Composable
fun WelcomeScreen(
    onNavigate: (WelcomeUiEvent) -> Unit,
) {
    val viewModel: WelcomeViewModel by rememberInstance()

    LaunchedEffect(viewModel.uiEvent) {
        viewModel.uiEvent.collect { uiEvent ->
            onNavigate(uiEvent)
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column {
            TopSection()
            Spacer(modifier = Modifier.height(64.dp))
            BottomSection(viewModel)
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
private fun BottomSection(viewModel: WelcomeViewModel) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize(),
    ) {
        AppButton(
            text = "Login",
            backgroundColor = Color(0xFFFFD700),
            onClick = viewModel::onLoginEvent,
        )
        Spacer(modifier = Modifier.height(20.dp))
        AppButton(
            text = "Create an Account",
            backgroundColor = Color.White,
            onClick = viewModel::onRegisterEvent,
        )
        Spacer(modifier = Modifier.height(20.dp))
        AppBottomText(
            firstText = "Don\'t want login?",
            secondText = "Skip",
            onClick = viewModel::onSkipEvent,
        )
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .width(80.dp)
                .height(32.dp)
                .border(
                    border = BorderStroke(
                        width = 1.dp,
                        color = Color(0xFFFFD700),
                    ),
                    shape = RoundedCornerShape(32.dp),
                ),
        ) {
            Box(
                modifier = Modifier
                    .width(40.dp)
                    .height(32.dp)
                    .background(
                        color = Color(0xFFFFD700),
                        shape = RoundedCornerShape(32.dp),
                    ),
            ) {
                AppText(
                    text = "TH",
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.Center),
                )
            }
            Box(
                modifier = Modifier
                    .width(40.dp)
                    .height(32.dp),
            ) {
                AppText(
                    text = "EN",
                    modifier = Modifier.align(Alignment.Center),
                )
            }
        }
    }
}