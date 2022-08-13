package com.adedom.authentication.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.adedom.authentication.presentation.event.RegisterUiEvent
import com.adedom.authentication.presentation.view_model.RegisterViewModel
import com.adedom.ui_components.AppBottomText
import org.kodein.di.compose.rememberInstance

@Composable
fun RegisterScreen(
    onNavigate: (RegisterUiEvent) -> Unit,
) {
    val viewModel: RegisterViewModel by rememberInstance()

    LaunchedEffect(viewModel) {
        viewModel.uiEvent.collect { uiEvent ->
            onNavigate(uiEvent)
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
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