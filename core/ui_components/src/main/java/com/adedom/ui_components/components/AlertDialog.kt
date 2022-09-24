package com.adedom.ui_components.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adedom.ui_components.theme.MyFoodTheme
import com.myfood.server.data.models.base.BaseError

@Composable
fun AppLoadingAlertDialog(
    modifier: Modifier = Modifier,
) {
    AlertDialog(
        onDismissRequest = {},
        confirmButton = {},
        backgroundColor = Color.Transparent,
        text = {
            AppLoadingLottieAnimation()
        },
        modifier = modifier.fillMaxSize(),
    )
}

@Composable
fun AppErrorAlertDialog(
    modifier: Modifier = Modifier,
    title: String = "Error!!!",
    error: BaseError,
    onDismiss: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = {},
        confirmButton = {},
        text = {
            Box(
                modifier = Modifier.fillMaxSize(),
            ) {
                Column {
                    AppTitleText(text = title)
                    AppText(text = error.message ?: "Unknown error")
                }
                AppText(
                    text = "OK",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .clickable {
                            onDismiss()
                        }
                )
            }
        },
        modifier = modifier
            .width(300.dp)
            .height(200.dp),
    )
}

@Composable
fun AppInteractAlertDialog(
    modifier: Modifier = Modifier,
    title: String,
    text: String? = null,
    confirmButton: () -> Unit,
    dismissButton: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = {},
        confirmButton = {
            AppText(
                text = "OK",
                modifier = Modifier.clickable(onClick = confirmButton),
            )
        },
        dismissButton = {
            AppText(
                text = "Cancel",
                modifier = Modifier.clickable(onClick = dismissButton),
            )
        },
        title = {
            AppTitleText(text = title)
        },
        text = {
            AppText(text = text.orEmpty())
        },
        modifier = modifier,
    )
}

@Composable
@Preview(showBackground = true)
fun AlertDialogPreview() {
    MyFoodTheme {
        AppLoadingAlertDialog()
    }
}