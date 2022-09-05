package com.adedom.welcome.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adedom.ui_components.theme.MyFoodTheme
import com.adedom.welcome.presentation.view_model.WelcomeUiAction

@Composable
fun WelcomeContent(
    dispatch: (WelcomeUiAction) -> Unit,
    openLoginPage: () -> Unit,
    openRegisterPage: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        TopSection()
        Spacer(modifier = Modifier.height(64.dp))
        BottomSection(
            dispatch,
            openLoginPage,
            openRegisterPage,
        )
    }
}

@Composable
@Preview(showBackground = true)
fun WelcomeContentPreview() {
    MyFoodTheme {
        WelcomeContent(
            dispatch = {},
            openLoginPage = {},
            openRegisterPage = {},
        )
    }
}