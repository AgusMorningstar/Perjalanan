package com.example.perjalanan.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = Maroon,
    onPrimary = White,
    primaryContainer = LightPeach,
    onPrimaryContainer = LightTextDark,
    secondary = SosRed,
    onSecondary = White,
    secondaryContainer = LightSoftRed,
    onSecondaryContainer = MaroonDark,
    tertiary = GreenSafe,
    onTertiary = White,
    background = LightCream,
    onBackground = LightTextDark,
    surface = LightCardBg,
    onSurface = LightTextDark,
    surfaceVariant = LightSoftCream,
    onSurfaceVariant = LightTextSecondary,
    outline = LightStroke,
    error = SosRed,
    onError = White
)

private val DarkColorScheme = darkColorScheme(
    primary = LightPeach,
    onPrimary = MaroonDark,
    primaryContainer = Maroon,
    onPrimaryContainer = White,
    secondary = BadgeRed,
    onSecondary = White,
    secondaryContainer = MaroonDark,
    onSecondaryContainer = DarkSoftPeach,
    tertiary = GreenSafe,
    onTertiary = White,
    background = DarkBackground,
    onBackground = DarkTextPrimary,
    surface = DarkSurface,
    onSurface = DarkTextPrimary,
    surfaceVariant = DarkCard,
    onSurfaceVariant = DarkTextSecondary,
    outline = DarkStroke,
    error = BadgeRed,
    onError = White
)

@Composable
fun PerjalananTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Keep branded maroon/cream identity consistent
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window
            if (window != null) {
                window.statusBarColor = if (darkTheme) DarkBackground.toArgb() else Maroon.toArgb()
                WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
