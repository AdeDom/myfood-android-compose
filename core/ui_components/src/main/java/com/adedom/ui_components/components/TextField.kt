package com.adedom.ui_components.components

import androidx.compose.animation.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adedom.ui_components.R
import com.adedom.ui_components.theme.MyFoodTheme
import com.adedom.ui_components.theme.RectangleLargeShape

@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    hint: String = "",
    error: String? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Default,
    singleLine: Boolean = true,
) {
    val focusManager = LocalFocusManager.current

    val visualTransformation = if (keyboardType == KeyboardType.Password) {
        PasswordVisualTransformation()
    } else {
        VisualTransformation.None
    }

    Column(
        modifier = modifier,
    ) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            label = {
                AppText(
                    text = hint,
                    color = Color.Gray,
                )
            },
            placeholder = {
                AppText(
                    text = hint,
                    color = Color.Gray,
                )
            },
            leadingIcon = leadingIcon,
            visualTransformation = visualTransformation,
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = imeAction,
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                },
            ),
            singleLine = singleLine,
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ),
            shape = RectangleLargeShape,
            modifier = Modifier.size(
                width = 300.dp,
                height = 60.dp,
            ),
        )
        Box(
            contentAlignment = Alignment.CenterEnd,
            modifier = Modifier.size(
                width = 300.dp,
                height = 20.dp,
            ),
        ) {
            this@Column.AnimatedVisibility(
                visible = !error.isNullOrBlank(),
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically(),
            ) {
                AppText(
                    text = error.orEmpty(),
                    color = Color.Red,
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.padding(end = 32.dp),
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun TextFieldPreview() {
    MyFoodTheme {
        var text by remember { mutableStateOf("") }
        AppTextField(
            value = text,
            onValueChange = {
                text = it
            },
            hint = stringResource(id = R.string.str_your_email),
            error = if (text.isEmpty()) stringResource(id = R.string.str_email_is_incorrect) else null,
        )
    }
}