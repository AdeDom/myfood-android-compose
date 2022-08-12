package com.adedom.splash_screen.presentation.splash_screen.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun WelcomeScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = "Welcome")
    }
}