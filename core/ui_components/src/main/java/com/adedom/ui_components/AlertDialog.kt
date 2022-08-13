package com.adedom.ui_components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.AlertDialog
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AppLoadingAlertDialog(
    modifier: Modifier = Modifier,
) {
    AlertDialog(
        onDismissRequest = {},
        confirmButton = {},
        text = {
            Box(
                modifier = Modifier.fillMaxSize(),
            ) {
                AppTitleText(text = "Loading...")
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                )
            }
        },
        modifier = modifier
            .width(200.dp)
            .height(200.dp),
    )
}