package com.adedom.authentication.presentation.component

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.adedom.authentication.presentation.event.RegisterUiEvent
import com.adedom.authentication.presentation.view_model.RegisterViewModel
import com.adedom.ui_components.*
import org.kodein.di.compose.rememberInstance

@Composable
fun RegisterScreen(
    onNavigate: (RegisterUiEvent) -> Unit,
) {
    val viewModel: RegisterViewModel by rememberInstance()

    val form by viewModel.form.collectAsState()

    LaunchedEffect(viewModel) {
        viewModel.uiEvent.collect { uiEvent ->
            onNavigate(uiEvent)
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth(),
        ) {
            AppTitleText(
                text = "Sign Up",
                modifier = Modifier.padding(top = 64.dp),
            )
            Spacer(modifier = Modifier.height(20.dp))
            AppSubTitleText("Add your details to sign up")
            Spacer(modifier = Modifier.height(20.dp))
            AppTextField(
                value = form.name,
                onValueChange = viewModel::setName,
                hint = "Name",
                imeAction = ImeAction.Next,
            )
            Spacer(modifier = Modifier.height(20.dp))
            AppTextField(
                value = form.email,
                onValueChange = viewModel::setEmail,
                hint = "Email",
                imeAction = ImeAction.Next,
            )
            Spacer(modifier = Modifier.height(20.dp))
            AppTextField(
                value = form.mobileNo,
                onValueChange = viewModel::setMobileNo,
                hint = "Mobile No",
                imeAction = ImeAction.Next,
            )
            Spacer(modifier = Modifier.height(20.dp))
            AppTextField(
                value = form.address,
                onValueChange = viewModel::setAddress,
                hint = "Address",
                imeAction = ImeAction.Next,
            )
            Spacer(modifier = Modifier.height(20.dp))
            AppTextField(
                value = form.password,
                onValueChange = viewModel::setPassword,
                hint = "Password",
                imeAction = ImeAction.Next,
            )
            Spacer(modifier = Modifier.height(20.dp))
            AppTextField(
                value = form.confirmPassword,
                onValueChange = viewModel::setConfirmPassword,
                hint = "Confirm Password",
                imeAction = ImeAction.Next,
            )
            Spacer(modifier = Modifier.height(20.dp))
            AppButton(
                text = "Sign Up",
                backgroundColor = Color(0xFFFFD700),
                borderColor = Color(0xFFFFD700),
                onClick = viewModel::onRegisterEvent,
            )
        }

        AppBottomText(
            firstText = "Already have an Account?",
            secondText = "Login",
            onClick = viewModel::onLoginEvent,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp),
        )
    }
}