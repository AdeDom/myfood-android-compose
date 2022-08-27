package com.adedom.authentication.presentation.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adedom.authentication.presentation.view_model.RegisterUiAction
import com.adedom.authentication.presentation.view_model.RegisterUiEvent
import com.adedom.authentication.presentation.view_model.RegisterUiState
import com.adedom.authentication.presentation.view_model.RegisterViewModel
import com.adedom.ui_components.components.*
import org.koin.androidx.compose.getViewModel

@Composable
fun RegisterScreen(
    onEvent: (RegisterUiEvent) -> Unit,
) {
    val viewModel: RegisterViewModel = getViewModel()

    LaunchedEffect(viewModel.uiEvent) {
        viewModel.uiEvent.collect { uiEvent ->
            onEvent(uiEvent)
        }
    }

    RegisterContent(
        state = viewModel.uiState,
        viewModel::dispatch,
    )
}

@Composable
fun RegisterContent(
    state: RegisterUiState,
    dispatch: (RegisterUiAction) -> Unit,
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
                onValueChange = { dispatch(RegisterUiAction.SetName(it)) },
                hint = "Name",
                imeAction = ImeAction.Next,
            )
            AppTextField(
                value = state.email,
                onValueChange = { dispatch(RegisterUiAction.SetEmail(it)) },
                hint = "Email",
                imeAction = ImeAction.Next,
            )
            AppTextField(
                value = state.mobileNo,
                onValueChange = { dispatch(RegisterUiAction.SetMobileNo(it)) },
                hint = "Mobile No",
                imeAction = ImeAction.Next,
            )
            AppTextField(
                value = state.address,
                onValueChange = { dispatch(RegisterUiAction.SetAddress(it)) },
                hint = "Address",
                imeAction = ImeAction.Next,
            )
            AppTextField(
                value = state.password,
                onValueChange = { dispatch(RegisterUiAction.SetPassword(it)) },
                hint = "Password",
                imeAction = ImeAction.Next,
            )
            AppTextField(
                value = state.confirmPassword,
                onValueChange = { dispatch(RegisterUiAction.SetConfirmPassword(it)) },
                hint = "Confirm Password",
                imeAction = ImeAction.Next,
            )
            AppButton(
                text = "Sign Up",
                backgroundColor = Color(0xFFFFD700),
                borderColor = Color(0xFFFFD700),
                onClick = { dispatch(RegisterUiAction.Submit) },
            )
            Spacer(modifier = Modifier.height(20.dp))
            AppBottomText(
                firstText = "Already have an Account?",
                secondText = "Login",
                onClick = { dispatch(RegisterUiAction.NavLogin) },
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
    )
}