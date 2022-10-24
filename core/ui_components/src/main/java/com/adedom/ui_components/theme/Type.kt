package com.adedom.ui_components.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.airbnb.android.showkase.annotation.ShowkaseTypography

@ShowkaseTypography(name = "h4", group = "Material Design")
val h4 = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Bold,
    fontSize = 34.sp,
)

@ShowkaseTypography(name = "h5", group = "Material Design")
val h5 = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Bold,
    fontSize = 24.sp,
)

@ShowkaseTypography(name = "h6", group = "Material Design")
val h6 = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Normal,
    fontSize = 20.sp,
)

@ShowkaseTypography(name = "subtitle1", group = "Material Design")
val subtitle1 = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Bold,
    fontSize = 16.sp,
)

@ShowkaseTypography(name = "body1", group = "Material Design")
val body1 = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Normal,
    fontSize = 16.sp,
)

@ShowkaseTypography(name = "body2", group = "Material Design")
val body2 = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Normal,
    fontSize = 14.sp,
)

val Typography = Typography(
    h4 = h4,
    h5 = h5,
    h6 = h6,
    subtitle1 = subtitle1,
    body1 = body1,
    body2 = body2,
)