package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val NovaDarkColorScheme = darkColorScheme(
    primary = PhosphorGreen,
    onPrimary = TerminalBlack,
    primaryContainer = PhosphorGreenDark,
    onPrimaryContainer = PhosphorGreen,
    secondary = AmberCrt,
    onSecondary = TerminalBlack,
    secondaryContainer = AmberCrtDark,
    onSecondaryContainer = AmberCrt,
    tertiary = CyberCyan,
    onTertiary = TerminalBlack,
    tertiaryContainer = CyberCyanMuted,
    onTertiaryContainer = CyberCyan,
    background = TerminalBlack,
    onBackground = TerminalTextPrimary,
    surface = TerminalSurface,
    onSurface = TerminalTextPrimary,
    surfaceVariant = TerminalSurfaceElevated,
    onSurfaceVariant = TerminalTextSecondary,
    error = AlertRed,
    onError = TerminalBlack,
    errorContainer = AlertRedDark,
    onErrorContainer = AlertRed
)

@Composable
fun MyApplicationTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = NovaDarkColorScheme,
        typography = Typography,
        content = content
    )
}
