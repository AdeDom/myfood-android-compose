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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adedom.ui_components.components.AppBottomText
import com.adedom.ui_components.components.AppButton
import com.adedom.ui_components.components.AppText
import com.adedom.ui_components.theme.MyFoodTheme

@Composable
fun BottomSection(
    onClickLogin: () -> Unit,
    onClickRegister: () -> Unit,
    onClickSkip: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize(),
    ) {
        AppButton(
            text = "Login",
            backgroundColor = Color(0xFFFFD700),
            onClick = onClickLogin,
        )
        Spacer(modifier = Modifier.height(20.dp))
        AppButton(
            text = "Create an Account",
            backgroundColor = Color.White,
            onClick = onClickRegister,
        )
        Spacer(modifier = Modifier.height(20.dp))
        AppBottomText(
            firstText = "Don\'t want login?",
            secondText = "Skip",
            onClick = onClickSkip,
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

@Composable
@Preview(showBackground = true)
fun BottomSectionPreview() {
    MyFoodTheme {
        BottomSection(
            onClickLogin = {},
            onClickRegister = {},
            onClickSkip = {},
        )
    }
}