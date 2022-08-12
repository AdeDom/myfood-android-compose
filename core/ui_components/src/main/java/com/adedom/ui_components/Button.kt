package com.adedom.ui_components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AppButton(
    text: String,
    backgroundColor: Color,
    borderColor: Color = Color(0xFFFFD700),
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    Card(
        shape = RoundedCornerShape(32.dp),
        backgroundColor = backgroundColor,
        border = BorderStroke(1.dp, borderColor),
        elevation = 2.dp,
        modifier = Modifier
            .width(300.dp)
            .height(60.dp)
            .clickable(enabled = enabled) {
                onClick()
            },
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            AppText(
                text,
                fontSize = 18.sp,
                modifier = Modifier.align(Alignment.Center),
            )
        }
    }
}