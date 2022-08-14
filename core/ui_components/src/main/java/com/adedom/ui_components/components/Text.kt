package com.adedom.ui_components.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AppText(
    text: String,
    color: Color = Color.Black,
    fontSize: TextUnit = 16.sp,
    fontWeight: FontWeight? = null,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        color = color,
        fontSize = fontSize,
        fontWeight = fontWeight,
        modifier = modifier,
    )
}

@Composable
fun AppTitleText(
    text: String,
    modifier: Modifier = Modifier,
) {
    AppText(
        text = text,
        fontSize = 24.sp,
        modifier = modifier,
    )
}

@Composable
fun AppSubTitleText(
    text: String,
    modifier: Modifier = Modifier,
) {
    AppText(
        text = text,
        color = Color.Gray,
        modifier = modifier,
    )
}

@Composable
fun AppBottomText(
    firstText: String,
    secondText: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier.clickable {
            onClick()
        }
    ) {
        AppText(
            firstText,
            color = Color.Gray,
        )
        Spacer(modifier = Modifier.width(4.dp))
        AppText(
            secondText,
            color = Color(0xFFFFD700),
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
fun AppErrorText(
    errorText: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .width(300.dp)
            .height(20.dp),
    ) {
        AppText(
            text = errorText,
            color = Color.Red,
            fontSize = 14.sp,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 32.dp),
        )
    }
}