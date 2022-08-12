package com.adedom.authentication.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.adedom.ui_components.AppBottomText

@Composable
fun RegisterScreen(
    onNavigate: () -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Text(text = "Register")

        AppBottomText(
            firstText = "Already have an Account?",
            secondText = "Login",
            onClick = onNavigate,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp),
        )
    }
}