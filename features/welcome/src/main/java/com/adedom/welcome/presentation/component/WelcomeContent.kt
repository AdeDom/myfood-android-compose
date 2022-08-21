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

@Composable
fun WelcomeContent(
    onClickLogin: () -> Unit,
    onClickRegister: () -> Unit,
    onClickSkip: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        TopSection()
        Spacer(modifier = Modifier.height(64.dp))
        BottomSection(
            onClickLogin = onClickLogin,
            onClickRegister = onClickRegister,
            onClickSkip = onClickSkip,
        )
    }
}

@Composable
@Preview(showBackground = true)
fun WelcomeContentPreview() {
    MyFoodTheme {
        WelcomeContent(
            onClickLogin = {},
            onClickRegister = {},
            onClickSkip = {},
        )
    }
}