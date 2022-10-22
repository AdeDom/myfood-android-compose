package com.adedom.authentication.presentation.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adedom.authentication.presentation.view_model.LoginUiEvent
import com.adedom.authentication.presentation.view_model.LoginUiState
import com.adedom.authentication.presentation.view_model.LoginViewModel
import com.adedom.ui_components.R
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
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth(),
        ) {
            AppTitleText(
                text = stringResource(id = R.string.str_login),
                modifier = Modifier.padding(top = 64.dp),
            )
            Spacer(modifier = Modifier.height(20.dp))
            AppSubTitleText(stringResource(id = R.string.str_add_your_details_to_login))
            Spacer(modifier = Modifier.height(20.dp))
            AppTextField(
                value = state.email,
                onValueChange = { dispatch(LoginUiEvent.SetEmail(it)) },
                hint = stringResource(id = R.string.str_your_email),
                error = if (state.isErrorEmail) stringResource(id = R.string.str_email_is_incorrect) else null,
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next,
            )
            AppTextField(
                value = state.password,
                onValueChange = { dispatch(LoginUiEvent.SetPassword(it)) },
                hint = stringResource(id = R.string.str_password),
                error = if (state.isErrorPassword) stringResource(id = R.string.str_password_is_incorrect) else null,
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done,
            )
            AppColorButton(
                text = stringResource(id = R.string.str_login),
                color = if (state.isLogin) MaterialTheme.colors.primary else Color.Gray,
                enabled = state.isLogin,
                onClick = { dispatch(LoginUiEvent.Submit) },
            )
            Spacer(modifier = Modifier.height(20.dp))
            AppText(
                text = stringResource(id = R.string.str_forget_your_password),
                color = Color.Gray,
            )
        }

        AppConcatText(
            firstText = stringResource(id = R.string.str_don_t_have_an_account),
            secondText = stringResource(id = R.string.str_sign_up),
            onClick = openRegisterPage,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp),
        )

        when (state.dialog) {
            LoginUiState.Dialog.Loading -> {
                AppLoadingAlertDialog(
                    modifier = Modifier.semantics {
                        contentDescription = "Loading dialog"
                    },
                )
            }
            is LoginUiState.Dialog.Error -> {
                AppErrorAlertDialog(
                    error = state.dialog.error,
                    onDismiss = { dispatch(LoginUiEvent.HideErrorDialog) },
                    modifier = Modifier.semantics { contentDescription = "Error dialog" },
                )
            }
            null -> {}
        }
    }
}

@Composable
@Preview(showBackground = true)
fun LoginContentPreview() {
    LoginContent(
        state = LoginUiState(
//            dialog = LoginUiState.Dialog.Loading,
//            dialog = LoginUiState.Dialog.Error(BaseError()),
        ),
        dispatch = {},
        openRegisterPage = {},
    )
}