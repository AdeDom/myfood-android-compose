package com.adedom.authentication.presentation.component

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adedom.authentication.presentation.view_model.RegisterUiEvent
import com.adedom.authentication.presentation.view_model.RegisterUiState
import com.adedom.authentication.presentation.view_model.RegisterViewModel
import com.adedom.ui_components.components.*
import com.adedom.ui_components.theme.MyFoodTheme
import com.adedom.ui_components.R as res

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel,
    openLoginPage: () -> Unit,
    openMainPage: () -> Unit,
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.nav.collect {
            openMainPage()
        }
    }

    RegisterContent(
        state = viewModel.uiState,
        viewModel::dispatch,
        openLoginPage,
    )
}

@Composable
fun RegisterContent(
    state: RegisterUiState,
    dispatch: (RegisterUiEvent) -> Unit,
    openLoginPage: () -> Unit,
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
                text = stringResource(id = res.string.str_sign_up),
                modifier = Modifier.padding(top = 64.dp),
            )
            Spacer(modifier = Modifier.height(20.dp))
            AppSubTitleText(stringResource(id = res.string.str_add_your_details_to_sign_up))
            Spacer(modifier = Modifier.height(20.dp))
            AppTextField(
                value = state.name,
                onValueChange = { dispatch(RegisterUiEvent.SetName(it)) },
                hint = stringResource(id = res.string.str_name),
                imeAction = ImeAction.Next,
            )
            AppTextField(
                value = state.email,
                onValueChange = { dispatch(RegisterUiEvent.SetEmail(it)) },
                hint = stringResource(id = res.string.str_email),
                imeAction = ImeAction.Next,
            )
            AppTextField(
                value = state.password,
                onValueChange = { dispatch(RegisterUiEvent.SetPassword(it)) },
                hint = stringResource(id = res.string.str_password),
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next,
            )
            AppTextField(
                value = state.confirmPassword,
                onValueChange = { dispatch(RegisterUiEvent.SetConfirmPassword(it)) },
                hint = stringResource(id = res.string.str_confirm_password),
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next,
            )
            AppColorButton(
                text = stringResource(id = res.string.str_sign_up),
                onClick = { dispatch(RegisterUiEvent.Submit) },
            )
        }

        AppConcatText(
            firstText = stringResource(id = res.string.str_already_have_an_account),
            secondText = stringResource(id = res.string.str_login),
            onClick = openLoginPage,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp),
        )

        when (state.dialog) {
            RegisterUiState.Dialog.Loading -> {
                AppLoadingAlertDialog(
                    modifier = Modifier.semantics {
                        contentDescription = "Loading dialog"
                    },
                )
            }
            is RegisterUiState.Dialog.Error -> {
                AppErrorAlertDialog(
                    error = state.dialog.error,
                    onDismiss = { dispatch(RegisterUiEvent.HideErrorDialog) },
                    modifier = Modifier.semantics { contentDescription = "Error dialog" },
                )
            }
            null -> {}
        }
    }
}

@Preview(
    name = "Register content",
    group = "Feature - Authentication",
    showBackground = true,
)
@Composable
fun RegisterContentPreview() {
    MyFoodTheme {
        RegisterContent(
            state = RegisterUiState(),
            dispatch = {},
            openLoginPage = {},
        )
    }
}