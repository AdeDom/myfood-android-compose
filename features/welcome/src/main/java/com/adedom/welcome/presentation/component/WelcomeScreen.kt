package com.adedom.welcome.presentation.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.adedom.welcome.presentation.view_model.WelcomeViewModel

@Composable
fun WelcomeScreen(
    viewModel: WelcomeViewModel,
    openLoginPage: () -> Unit,
    openRegisterPage: () -> Unit,
    openMainPage: () -> Unit,
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.nav.collect {
            openMainPage()
        }
    }

    WelcomeContent(
        viewModel::dispatch,
        openLoginPage,
        openRegisterPage,
    )
}