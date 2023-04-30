package com.adedom.ui_components.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adedom.ui_components.R
import com.adedom.ui_components.theme.MyFoodTheme

@Composable
fun AppText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Black,
    style: TextStyle = MaterialTheme.typography.bodyLarge,
) {
    Text(
        text = text,
        color = color,
        style = style,
        modifier = modifier,
    )
}

@Composable
fun AppTitleText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Black,
    style: TextStyle = MaterialTheme.typography.headlineMedium,
) {
    Text(
        text = text,
        color = color,
        style = style,
        modifier = modifier,
    )
}

@Composable
fun AppSubTitleText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Gray,
    style: TextStyle = MaterialTheme.typography.headlineSmall,
) {
    Text(
        text = text,
        color = color,
        style = style,
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
            style = MaterialTheme.typography.bodyLarge,
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = secondText,
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleLarge,
        )
    }
}

@Preview(
    name = "Text",
    group = "Component - Text",
    showBackground = true,
)
@Composable
fun AppTextPreview() {
    MyFoodTheme {
        AppText(text = stringResource(id = R.string.app_name))
    }
}

@Preview(
    name = "Title text",
    group = "Component - Text",
    showBackground = true,
)
@Composable
fun AppTitleTextPreview() {
    MyFoodTheme {
        AppTitleText(text = stringResource(id = R.string.app_name))
    }
}

@Preview(
    name = "Sub title text",
    group = "Component - Text",
    showBackground = true,
)
@Composable
fun AppSubTitleTextPreview() {
    MyFoodTheme {
        AppSubTitleText(text = stringResource(id = R.string.app_name))
    }
}

@Preview(
    name = "Concat text",
    group = "Component - Text",
    showBackground = true,
)
@Composable
fun AppConcatTextPreview() {
    MyFoodTheme {
        AppConcatText(
            firstText = stringResource(id = R.string.str_don_t_want_login),
            secondText = stringResource(id = R.string.str_skip),
            onClick = {},
        )
    }
}