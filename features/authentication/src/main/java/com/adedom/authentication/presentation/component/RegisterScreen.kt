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
import com.adedom.authentication.presentation.event.RegisterUiEvent
import com.adedom.authentication.presentation.state.RegisterUiState
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
        onNameChange = viewModel::setName,
        onEmailChange = viewModel::setEmail,
        onMobileNoChange = viewModel::setMobileNo,
        onAddressChange = viewModel::setAddress,
        onPasswordChange = viewModel::setPassword,
        onConfirmPasswordChange = viewModel::setConfirmPassword,
        onRegisterClick = viewModel::onRegisterEvent,
        onLoginClick = viewModel::onLoginEvent,
    )
}

@Composable
fun RegisterContent(
    state: RegisterUiState,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onMobileNoChange: (String) -> Unit,
    onAddressChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onRegisterClick: () -> Unit,
    onLoginClick: () -> Unit,
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
                onValueChange = onNameChange,
                hint = "Name",
                imeAction = ImeAction.Next,
            )
            AppTextField(
                value = state.email,
                onValueChange = onEmailChange,
                hint = "Email",
                imeAction = ImeAction.Next,
            )
            AppTextField(
                value = state.mobileNo,
                onValueChange = onMobileNoChange,
                hint = "Mobile No",
                imeAction = ImeAction.Next,
            )
            AppTextField(
                value = state.address,
                onValueChange = onAddressChange,
                hint = "Address",
                imeAction = ImeAction.Next,
            )
            AppTextField(
                value = state.password,
                onValueChange = onPasswordChange,
                hint = "Password",
                imeAction = ImeAction.Next,
            )
            AppTextField(
                value = state.confirmPassword,
                onValueChange = onConfirmPasswordChange,
                hint = "Confirm Password",
                imeAction = ImeAction.Next,
            )
            AppButton(
                text = "Sign Up",
                backgroundColor = Color(0xFFFFD700),
                borderColor = Color(0xFFFFD700),
                onClick = onRegisterClick,
            )
            Spacer(modifier = Modifier.height(20.dp))
            AppBottomText(
                firstText = "Already have an Account?",
                secondText = "Login",
                onClick = onLoginClick,
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
        onNameChange = {},
        onEmailChange = {},
        onMobileNoChange = {},
        onAddressChange = {},
        onPasswordChange = {},
        onConfirmPasswordChange = {},
        onRegisterClick = {},
        onLoginClick = {},
    )
}