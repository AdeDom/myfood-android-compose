package com.adedom.ui_components.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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

    Column {
        TextField(
            value = value,
            onValueChange = onValueChange,
            label = {
                Text(hint)
            },
            placeholder = {
                Text(text = hint)
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
            shape = RoundedCornerShape(32.dp),
            modifier = modifier,
        )
        if (error.isNullOrBlank()) {
            Spacer(modifier = Modifier.height(20.dp))
        } else {
            Box(
                modifier = Modifier.size(
                    width = 300.dp,
                    height = 20.dp,
                ),
            ) {
                AppText(
                    text = error,
                    color = Color.Red,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 32.dp),
                )
            }
        }
    }
}