package com.adedom.welcome.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adedom.ui_components.theme.MyFoodTheme
import com.adedom.welcome.presentation.view_model.WelcomeUiEvent

@Composable
fun WelcomeContent(
    onEvent: (WelcomeUiEvent) -> Unit,
    openLoginPage: () -> Unit,
    openRegisterPage: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        WelcomeTopSection(modifier = Modifier.semantics { contentDescription = "Welcome top section" })
        Spacer(modifier = Modifier.height(64.dp))
        WelcomeBottomSection(
            openLoginPage = openLoginPage,
            openRegisterPage = openRegisterPage,
            openHomePage = { onEvent(WelcomeUiEvent.NavSkip) },
            onChangeLanguage = { onEvent(WelcomeUiEvent.OnChangeLanguage(it)) },
            modifier = Modifier.semantics { contentDescription = "Welcome bottom section" },
        )
    }
}

@Preview(
    name = "Welcome content",
    group = "Feature - Welcome",
    showBackground = true,
)
@Composable
fun WelcomeContentPreview() {
    MyFoodTheme {
        WelcomeContent(
            onEvent = {},
            openLoginPage = {},
            openRegisterPage = {},
        )
    }
}