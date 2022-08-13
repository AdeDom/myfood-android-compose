package com.adedom.authentication.presentation.component

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.adedom.authentication.presentation.event.LoginUiEvent
import com.adedom.authentication.presentation.state.LoginUiState
import com.adedom.authentication.presentation.view_model.LoginViewModel
import com.adedom.ui_components.*
import org.kodein.di.compose.rememberInstance

@Composable
fun LoginScreen(
    onNavigate: (LoginUiEvent) -> Unit,
) {
    val viewModel: LoginViewModel by rememberInstance()

    val uiState by viewModel.uiState.collectAsState()
    val form by viewModel.form.collectAsState()

    val context = LocalContext.current

    LaunchedEffect(viewModel) {
        viewModel.uiEvent.collect { uiEvent ->
            onNavigate(uiEvent)
        }
    }

    when (uiState) {
        LoginUiState.Initial -> {
            LoginRender(
                viewModel = viewModel,
                form = form,
            )
        }
        is LoginUiState.ValidateEmail -> {
            val state = uiState as LoginUiState.ValidateEmail
            LoginRender(
                viewModel = viewModel,
                form = form,
                isErrorEmail = state.isError,
                isLogin = state.isLogin,
            )
        }
        is LoginUiState.ValidatePassword -> {
            val state = uiState as LoginUiState.ValidatePassword
            LoginRender(
                viewModel = viewModel,
                form = form,
                isErrorPassword = state.isError,
                isLogin = state.isLogin,
            )
        }
        LoginUiState.Loading -> {
            LoginRender(
                viewModel = viewModel,
                form = form,
                isLoading = true,
            )
        }
        is LoginUiState.LoginError -> {
            val state = uiState as LoginUiState.LoginError
            val errorMessage = state.error.message ?: state.error.code.orEmpty()
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()

            LoginRender(
                viewModel = viewModel,
                form = form,
                isLogin = true,
                isLoading = false,
            )
        }
    }
}

@Composable
fun LoginRender(
    viewModel: LoginViewModel,
    form: LoginUiState.LoginForm,
    isErrorEmail: Boolean = false,
    isErrorPassword: Boolean = false,
    isLogin: Boolean = false,
    isLoading: Boolean = false,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        if (isLoading) {
            CircularProgressIndicator()
        }

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth(),
        ) {
            AppText(
                text = "Login",
                color = Color.Gray,
                fontSize = 24.sp,
                modifier = Modifier.padding(top = 64.dp),
            )
            Spacer(modifier = Modifier.height(20.dp))
            AppText(
                text = "Add your details to login",
                color = Color.Gray,
            )
            Spacer(modifier = Modifier.height(20.dp))
            AppTextField(
                value = form.email,
                onValueChange = { email ->
                    viewModel.setEmail(email)
                    viewModel.onValidateEmail()
                },
                hint = "Your Email",
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next,
            )
            if (isErrorEmail) {
                AppErrorText("Email is incorrect")
            } else {
                Spacer(modifier = Modifier.height(20.dp))
            }
            AppTextField(
                value = form.password,
                onValueChange = { password ->
                    viewModel.setPassword(password)
                    viewModel.onValidatePassword()
                },
                hint = "Password",
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done,
            )
            if (isErrorPassword) {
                AppErrorText("Password is incorrect")
            } else {
                Spacer(modifier = Modifier.height(20.dp))
            }
            AppButton(
                text = "Login",
                backgroundColor = if (isLogin) Color(0xFFFFD700) else Color.Gray,
                borderColor = if (isLogin) Color(0xFFFFD700) else Color.Gray,
                enabled = isLogin,
                onClick = viewModel::onLoginEvent,
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
            onClick = viewModel::onRegisterEvent,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp),
        )
    }
}