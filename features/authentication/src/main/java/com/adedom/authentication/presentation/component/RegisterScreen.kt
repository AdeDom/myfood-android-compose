package com.adedom.authentication.presentation.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adedom.authentication.presentation.view_model.RegisterUiEvent
import com.adedom.authentication.presentation.view_model.RegisterUiState
import com.adedom.authentication.presentation.view_model.RegisterViewModel
import com.adedom.ui_components.components.*

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel,
    openLoginPage: () -> Unit,
    openMainPage: () -> Unit,
) {
    RegisterContent(
        state = viewModel.uiState,
        viewModel::dispatch,
        openLoginPage,
        openMainPage,
    )
}

@Composable
fun RegisterContent(
    state: RegisterUiState,
    dispatch: (RegisterUiEvent) -> Unit,
    openLoginPage: () -> Unit,
    openMainPage: () -> Unit,
) {
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize(),
    ) {
        item {
            AppTitleText(
                text = "Sign Up",
                modifier = Modifier.padding(top = 64.dp),
            )
            Spacer(modifier = Modifier.height(20.dp))
            AppSubTitleText("Add your details to sign up")
            Spacer(modifier = Modifier.height(20.dp))
            AppTextField(
                value = state.name,
                onValueChange = { dispatch(RegisterUiEvent.SetName(it)) },
                hint = "Name",
                imeAction = ImeAction.Next,
            )
            AppTextField(
                value = state.email,
                onValueChange = { dispatch(RegisterUiEvent.SetEmail(it)) },
                hint = "Email",
                imeAction = ImeAction.Next,
            )
            AppTextField(
                value = state.mobileNo,
                onValueChange = { dispatch(RegisterUiEvent.SetMobileNo(it)) },
                hint = "Mobile No",
                imeAction = ImeAction.Next,
            )
            AppTextField(
                value = state.address,
                onValueChange = { dispatch(RegisterUiEvent.SetAddress(it)) },
                hint = "Address",
                imeAction = ImeAction.Next,
            )
            AppTextField(
                value = state.password,
                onValueChange = { dispatch(RegisterUiEvent.SetPassword(it)) },
                hint = "Password",
                imeAction = ImeAction.Next,
            )
            AppTextField(
                value = state.confirmPassword,
                onValueChange = { dispatch(RegisterUiEvent.SetConfirmPassword(it)) },
                hint = "Confirm Password",
                imeAction = ImeAction.Next,
            )
            AppColorButton(
                text = "Sign Up",
                onClick = openMainPage,
            )
            Spacer(modifier = Modifier.height(20.dp))
            AppBottomText(
                firstText = "Already have an Account?",
                secondText = "Login",
                onClick = openLoginPage,
                modifier = Modifier.padding(bottom = 16.dp),
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun RegisterContentPreview() {
    RegisterContent(
        state = RegisterUiState(),
        dispatch = {},
        openLoginPage = {},
        openMainPage = {},
    )
}