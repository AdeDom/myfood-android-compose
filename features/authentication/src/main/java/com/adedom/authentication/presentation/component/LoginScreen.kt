package com.adedom.authentication.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun LoginScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Text(text = "Login")
    }
}