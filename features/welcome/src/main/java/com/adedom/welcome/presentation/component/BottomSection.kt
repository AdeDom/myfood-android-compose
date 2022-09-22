package com.adedom.welcome.presentation.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adedom.ui_components.components.AppBottomText
import com.adedom.ui_components.components.AppButton
import com.adedom.ui_components.components.AppText
import com.adedom.ui_components.theme.MyFoodTheme
import com.adedom.welcome.presentation.view_model.WelcomeUiEvent

@Composable
fun BottomSection(
    dispatch: (WelcomeUiEvent) -> Unit,
    openLoginPage: () -> Unit,
    openRegisterPage: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .testTag("Welcome bottom section root column"),
    ) {
        AppButton(
            text = "Login",
            backgroundColor = Color(0xFFFFD700),
            onClick = openLoginPage,
            modifier = Modifier.testTag("Login button"),
        )
        Spacer(
            modifier = Modifier
                .height(20.dp)
                .testTag("Space column1"),
        )
        AppButton(
            text = "Create an Account",
            backgroundColor = Color.White,
            onClick = openRegisterPage,
            modifier = Modifier.testTag("Create an account button"),
        )
        Spacer(
            modifier = Modifier
                .height(20.dp)
                .testTag("Space column2"),
        )
        AppBottomText(
            firstText = "Don\'t want login?",
            secondText = "Skip",
            onClick = { dispatch(WelcomeUiEvent.NavSkip) },
            modifier = Modifier.testTag("Don't want login? skip test"),
        )
        Spacer(
            modifier = Modifier
                .height(20.dp)
                .testTag("Space column3"),
        )
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
                )
                .testTag("Row change language"),
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .width(40.dp)
                    .height(32.dp)
                    .background(
                        color = Color(0xFFFFD700),
                        shape = RoundedCornerShape(32.dp),
                    )
                    .testTag("Box change language"),
            ) {
                AppText(
                    text = "TH",
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.testTag("Text th"),
                )
            }
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .width(40.dp)
                    .height(32.dp)
                    .testTag("Box en"),
            ) {
                AppText(
                    text = "EN",
                    modifier = Modifier.testTag("Text en"),
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun BottomSectionPreview() {
    MyFoodTheme {
        BottomSection(
            dispatch = {},
            openLoginPage = {},
            openRegisterPage = {},
        )
    }
}