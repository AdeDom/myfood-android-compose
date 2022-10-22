package com.adedom.ui_components.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.adedom.ui_components.theme.MyFoodTheme

@Composable
fun AppText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Black,
    fontSize: TextUnit = 16.sp,
    fontWeight: FontWeight? = null,
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
    color: Color = Color.Black,
    fontSize: TextUnit = 24.sp,
) {
    Text(
        text = text,
        color = color,
        fontSize = fontSize,
        modifier = modifier,
    )
}

@Composable
fun AppSubTitleText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Gray,
    fontSize: TextUnit = 16.sp,
) {
    Text(
        text = text,
        color = color,
        fontSize = fontSize,
        modifier = modifier,
    )
}

@Composable
fun AppConcatText(
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
        Text(
            text = firstText,
            color = Color.Gray,
            fontSize = 16.sp,
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = secondText,
            color = MaterialTheme.colors.primary,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Preview
@Composable
fun AppTextPreview() {
    MyFoodTheme {
        AppText(text = "Hello world")
    }
}

@Preview
@Composable
fun AppTitleTextPreview() {
    MyFoodTheme {
        AppTitleText(text = "Hello world")
    }
}

@Preview
@Composable
fun AppSubTitleTextPreview() {
    MyFoodTheme {
        AppSubTitleText(text = "Hello world")
    }
}

@Preview
@Composable
fun AppConcatTextPreview() {
    MyFoodTheme {
        AppConcatText(
            firstText = "Hello",
            secondText = "World",
            onClick = {},
        )
    }
}