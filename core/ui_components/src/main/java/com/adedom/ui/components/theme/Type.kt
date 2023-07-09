package com.adedom.ui.components.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.airbnb.android.showkase.annotation.ShowkaseTypography

object AppTypography {

    @ShowkaseTypography
    val headlineLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 34.sp,
        lineHeight = 40.sp,
        letterSpacing = 0.sp
    )

    @ShowkaseTypography
    val headlineMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.sp
    )

    @ShowkaseTypography
    val headlineSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp
    )

    @ShowkaseTypography
    val titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    )

    @ShowkaseTypography
    val bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )

    @ShowkaseTypography
    val bodyMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.2.sp
    )
}

val Typography = Typography(
    headlineLarge = AppTypography.headlineLarge,
    headlineMedium = AppTypography.headlineMedium,
    headlineSmall = AppTypography.headlineSmall,
    titleLarge = AppTypography.titleLarge,
    bodyLarge = AppTypography.bodyLarge,
    bodyMedium = AppTypography.bodyMedium
)
