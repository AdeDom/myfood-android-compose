package com.adedom.ui_components.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adedom.ui_components.R
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
    title: String = stringResource(id = R.string.str_error_default),
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
                    AppText(text = error.message ?: stringResource(id = R.string.str_error_unknown))
                }
                AppText(
                    text = stringResource(id = R.string.str_ok),
                    style = MaterialTheme.typography.subtitle1,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .clickable {
                            onDismiss()
                        }
                )
            }
        },
        modifier = modifier.size(
            width = 300.dp,
            height = 200.dp,
        ),
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
                text = stringResource(id = R.string.str_ok),
                modifier = Modifier.clickable(onClick = confirmButton),
            )
        },
        dismissButton = {
            AppText(
                text = stringResource(id = R.string.str_cancel),
                modifier = Modifier.clickable(onClick = dismissButton),
            )
        },
        title = {
            AppTitleText(text = title)
        },
        text = {
            text?.let { AppText(text = text) }
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