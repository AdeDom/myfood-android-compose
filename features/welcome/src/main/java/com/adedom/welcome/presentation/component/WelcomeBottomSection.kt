package com.adedom.welcome.presentation.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adedom.ui_components.components.AppColorButton
import com.adedom.ui_components.components.AppConcatText
import com.adedom.ui_components.components.AppOutlinedButton
import com.adedom.ui_components.components.AppText
import com.adedom.ui_components.theme.MyFoodTheme
import com.adedom.ui_components.theme.RectangleLargeShape
import com.adedom.welcome.presentation.view_model.WelcomeUiEvent
import com.adedom.ui_components.R as res

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
            text = stringResource(id = res.string.str_login),
            onClick = openLoginPage,
        )
        Spacer(modifier = Modifier.height(20.dp))
        AppOutlinedButton(
            text = stringResource(id = res.string.str_create_an_account),
            onClick = openRegisterPage,
        )
        Spacer(modifier = Modifier.height(20.dp))
        AppConcatText(
            firstText = stringResource(id = res.string.str_don_t_want_login),
            secondText = stringResource(id = res.string.str_skip),
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
                        color = MaterialTheme.colors.primary,
                    ),
                    shape = RectangleLargeShape,
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
                        color = MaterialTheme.colors.primary,
                        shape = RectangleLargeShape,
                    )
                    .semantics {
                        contentDescription = "Background change language th"
                    },
            ) {
                AppText(
                    text = stringResource(id = res.string.str_th),
                    color = Color.White,
                    style = MaterialTheme.typography.subtitle1,
                )
            }
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(
                    width = 40.dp,
                    height = 32.dp,
                ),
            ) {
                AppText(text = stringResource(id = res.string.str_en))
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