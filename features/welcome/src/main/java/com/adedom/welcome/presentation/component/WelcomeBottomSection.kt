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
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adedom.ui_components.components.AppBottomText
import com.adedom.ui_components.components.AppColorButton
import com.adedom.ui_components.components.AppOutlinedButton
import com.adedom.ui_components.components.AppText
import com.adedom.ui_components.theme.MyFoodTheme
import com.adedom.welcome.presentation.view_model.WelcomeUiEvent

@Composable
fun WelcomeBottomSection(
    dispatch: (WelcomeUiEvent) -> Unit,
    openLoginPage: () -> Unit,
    openRegisterPage: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize(),
    ) {
        AppColorButton(
            text = "Login",
            onClick = openLoginPage,
        )
        Spacer(modifier = Modifier.height(20.dp))
        AppOutlinedButton(
            text = "Create an Account",
            onClick = openRegisterPage,
        )
        Spacer(modifier = Modifier.height(20.dp))
        AppBottomText(
            firstText = "Don\'t want login?",
            secondText = "Skip",
            onClick = { dispatch(WelcomeUiEvent.NavSkip) },
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
                )
                .semantics {
                    contentDescription = "Border change language"
                },
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
                    .semantics {
                        contentDescription = "Background change language th"
                    },
            ) {
                AppText(
                    text = "TH",
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                )
            }
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(
                    width = 40.dp,
                    height = 32.dp,
                ),
            ) {
                AppText(text = "EN")
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun WelcomeBottomSectionPreview() {
    MyFoodTheme {
        WelcomeBottomSection(
            dispatch = {},
            openLoginPage = {},
            openRegisterPage = {},
        )
    }
}