package com.example.wirelessusb.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Win98 Light
private val Win98Light = lightColorScheme(
    primary = Color(0xFF000080),
    onPrimary = Color.White,
    primaryContainer = Color(0xFF000080),
    onPrimaryContainer = Color.White,
    background = Color(0xFFC0C0C0),
    onBackground = Color(0xFF000000),
    surface = Color(0xFFC0C0C0),
    onSurface = Color(0xFF000000),
    surfaceVariant = Color(0xFFC0C0C0),
    onSurfaceVariant = Color(0xFF000000),
    outline = Color(0xFF808080),
    secondary = Color(0xFF000080),
    onSecondary = Color.White,
)

// Win98 Dark (same gray feel but darker)
private val Win98Dark = darkColorScheme(
    primary = Color(0xFF1084D0),
    onPrimary = Color.White,
    primaryContainer = Color(0xFF1084D0),
    onPrimaryContainer = Color.White,
    background = Color(0xFF3A3A3A),
    onBackground = Color(0xFFFFFFFF),
    surface = Color(0xFF4A4A4A),
    onSurface = Color(0xFFFFFFFF),
    surfaceVariant = Color(0xFF4A4A4A),
    onSurfaceVariant = Color(0xFFCCCCCC),
    outline = Color(0xFF666666),
    secondary = Color(0xFF1084D0),
    onSecondary = Color.White,
)

@Composable
fun WirelessUSBTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) Win98Dark else Win98Light

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
