package com.adedom.authentication.presentation.component

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adedom.authentication.presentation.view_model.LoginUiEvent
import com.adedom.authentication.presentation.view_model.LoginUiState
import com.adedom.authentication.presentation.view_model.LoginViewModel
import com.adedom.ui_components.components.*

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    openRegisterPage: () -> Unit,
    openMainPage: () -> Unit,
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.nav.collect {
            openMainPage()
        }
    }

    LoginContent(
        state = viewModel.uiState,
        viewModel::dispatch,
        openRegisterPage,
    )
}

@Composable
fun LoginContent(
    state: LoginUiState,
    dispatch: (LoginUiEvent) -> Unit,
    openRegisterPage: () -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        if (state.isLoading) {
            AppLoadingAlertDialog()
        }

        state.error?.let { error ->
            AppErrorAlertDialog(
                error = error,
                onDismiss = { dispatch(LoginUiEvent.HideErrorDialog) },
            )
        }

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth(),
        ) {
            AppTitleText(
                text = "Login",
                modifier = Modifier.padding(top = 64.dp),
            )
            Spacer(modifier = Modifier.height(20.dp))
            AppSubTitleText("Add your details to login")
            Spacer(modifier = Modifier.height(20.dp))
            AppTextField(
                value = state.email,
                onValueChange = { dispatch(LoginUiEvent.SetEmail(it)) },
                hint = "Your Email",
                error = if (state.isErrorEmail) "Email is incorrect" else null,
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next,
                modifier = Modifier.size(
                    width = 300.dp,
                    height = 60.dp,
                )
            )
            AppTextField(
                value = state.password,
                onValueChange = { dispatch(LoginUiEvent.SetPassword(it)) },
                hint = "Password",
                error = if (state.isErrorPassword) "Password is incorrect" else null,
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done,
                modifier = Modifier.size(
                    width = 300.dp,
                    height = 60.dp,
                )
            )
            AppButton(
                text = "Login",
                backgroundColor = if (state.isLogin) Color(0xFFFFD700) else Color.Gray,
                borderColor = if (state.isLogin) Color(0xFFFFD700) else Color.Gray,
                enabled = state.isLogin,
                onClick = { dispatch(LoginUiEvent.Submit) },
            )
            Spacer(modifier = Modifier.height(20.dp))
            AppText(
                text = "Forget your password?",
                color = Color.Gray,
            )
        }

        AppBottomText(
            firstText = "Don\'t have an Account?",
            secondText = "Sign Up",
            onClick = openRegisterPage,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp),
        )
    }
}

@Composable
@Preview(showBackground = true)
fun LoginContentPreview() {
    LoginContent(
        state = LoginUiState(
//            isLoading = true,
        ),
        dispatch = {},
        openRegisterPage = {},
    )
}