package com.adedom.main.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun MainScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Text("Main")
    }
}