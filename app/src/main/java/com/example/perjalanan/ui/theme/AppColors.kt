package com.example.perjalanan.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

/**
 * Kumpulan warna yang berganti sesuai tema (Terang/Gelap).
 * Diakses via LocalAppColors.current di setiap Composable.
 */
data class AppColors(
    val screenBackground: Color,   // Latar belakang layar utama (LightCream / DarkBackground)
    val cardBackground: Color,     // Latar kartu / surface (LightCardBg / DarkCard)
    val surfaceBackground: Color,  // Surface (LightSoftCream / DarkSurface)
    val strokeColor: Color,        // Warna border/outline (LightStroke / DarkStroke)
    val textPrimary: Color,        // Teks utama (LightTextDark / DarkTextPrimary)
    val textSecondary: Color,      // Teks sekunder (LightTextGray / DarkTextSecondary)
    val peach: Color,              // Latar avatar / aksen (LightPeach / DarkPeach)
    val softPeach: Color,          // Latar item aktif nav (LightSoftPeach / DarkSoftPeach)
    val softRed: Color,            // Latar peringatan merah (LightSoftRed / DarkSoftRed)
    val softGreen: Color,          // Latar status aman (LightSoftGreen / DarkSoftGreen)
    val isDark: Boolean
)

val LightAppColors = AppColors(
    screenBackground = LightCream,
    cardBackground   = LightCardBg,
    surfaceBackground = LightSoftCream,
    strokeColor      = LightStroke,
    textPrimary      = LightTextDark,
    textSecondary    = LightTextGray,
    peach            = LightPeach,
    softPeach        = LightSoftPeach,
    softRed          = LightSoftRed,
    softGreen        = LightSoftGreen,
    isDark           = false
)

val DarkAppColors = AppColors(
    screenBackground = DarkBackground,
    cardBackground   = DarkCard,
    surfaceBackground = DarkSurface,
    strokeColor      = DarkStroke,
    textPrimary      = DarkTextPrimary,
    textSecondary    = DarkTextSecondary,
    peach            = DarkPeach,
    softPeach        = DarkSoftPeach,
    softRed          = DarkSoftRed,
    softGreen        = DarkSoftGreen,
    isDark           = true
)

val LocalAppColors = staticCompositionLocalOf<AppColors> { LightAppColors }
