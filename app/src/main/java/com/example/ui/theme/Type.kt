package com.example.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// NOVA 1990s Intelligence Monospace Typography
val TerminalFontFamily = FontFamily.Monospace

val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = TerminalFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        lineHeight = 34.sp,
        letterSpacing = 1.2.sp,
        color = PhosphorGreen
    ),
    displayMedium = TextStyle(
        fontFamily = TerminalFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 1.sp,
        color = PhosphorGreen
    ),
    headlineMedium = TextStyle(
        fontFamily = TerminalFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.8.sp,
        color = TerminalTextPrimary
    ),
    titleMedium = TextStyle(
        fontFamily = TerminalFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 15.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.6.sp,
        color = AmberCrt
    ),
    titleSmall = TextStyle(
        fontFamily = TerminalFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 13.sp,
        lineHeight = 18.sp,
        letterSpacing = 0.5.sp,
        color = TerminalTextPrimary
    ),
    bodyLarge = TextStyle(
        fontFamily = TerminalFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.4.sp,
        color = TerminalTextPrimary
    ),
    bodyMedium = TextStyle(
        fontFamily = TerminalFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.3.sp,
        color = TerminalTextPrimary
    ),
    bodySmall = TextStyle(
        fontFamily = TerminalFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp,
        lineHeight = 14.sp,
        letterSpacing = 0.2.sp,
        color = TerminalTextSecondary
    ),
    labelLarge = TextStyle(
        fontFamily = TerminalFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 1.sp,
        color = PhosphorGreen
    ),
    labelMedium = TextStyle(
        fontFamily = TerminalFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 10.sp,
        lineHeight = 14.sp,
        letterSpacing = 0.6.sp,
        color = TerminalTextSecondary
    ),
    labelSmall = TextStyle(
        fontFamily = TerminalFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 9.sp,
        lineHeight = 12.sp,
        letterSpacing = 0.5.sp,
        color = TerminalTextDim
    )
)
