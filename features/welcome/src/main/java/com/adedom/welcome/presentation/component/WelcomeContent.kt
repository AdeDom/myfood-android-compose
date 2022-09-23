package com.adedom.welcome.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adedom.ui_components.theme.MyFoodTheme
import com.adedom.welcome.presentation.view_model.WelcomeUiEvent

@Composable
fun WelcomeContent(
    dispatch: (WelcomeUiEvent) -> Unit,
    openLoginPage: () -> Unit,
    openRegisterPage: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        TopSection(modifier = Modifier.testTag("Welcome top section"))
        Spacer(modifier = Modifier.height(64.dp))
        BottomSection(
            dispatch = dispatch,
            openLoginPage = openLoginPage,
            openRegisterPage = openRegisterPage,
            modifier = Modifier.testTag("Welcome bottom section"),
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