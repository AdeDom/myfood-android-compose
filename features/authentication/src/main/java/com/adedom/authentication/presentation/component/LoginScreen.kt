package com.adedom.authentication.presentation.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.adedom.authentication.presentation.event.LoginUiEvent
import com.adedom.authentication.presentation.view_model.LoginViewModel
import com.adedom.ui_components.AppButton
import com.adedom.ui_components.AppText
import com.adedom.ui_components.BottomText
import org.kodein.di.compose.rememberInstance

@Composable
fun LoginScreen(
    onNavigate: (LoginUiEvent) -> Unit,
) {
    val viewModel: LoginViewModel by rememberInstance()

    val form by viewModel.form.collectAsState()

    val focusManager = LocalFocusManager.current

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
            TextField(
                value = form.email,
                onValueChange = viewModel::setEmail,
                placeholder = {
                    Text(text = "Your Email")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next,
                ),
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                shape = RoundedCornerShape(32.dp),
                modifier = Modifier
                    .width(300.dp)
                    .height(60.dp),
            )
            Spacer(modifier = Modifier.height(20.dp))
            TextField(
                value = form.password,
                onValueChange = viewModel::setPassword,
                placeholder = {
                    Text(text = "Password")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions {
                    focusManager.clearFocus()
                },
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                shape = RoundedCornerShape(32.dp),
                modifier = Modifier
                    .width(300.dp)
                    .height(60.dp),
            )
            Spacer(modifier = Modifier.height(20.dp))
            AppButton(
                text = "Login",
                backgroundColor = Color(0xFFFFD700),
                onClick = viewModel::onLoginEvent,
            )
            Spacer(modifier = Modifier.height(20.dp))
            AppText(
                text = "Forget your password?",
                color = Color.Gray,
            )
        }

        BottomText(
            firstText = "Don\'t have an Account?",
            secondText = "Sign Up",
            onClick = viewModel::onRegisterEvent,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp),
        )
    }
}