package com.example.perjalanan.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color

// =========================================================================
// BRAND IDENTITAS TETAP (Tidak Berubah Antara Terang / Gelap)
// =========================================================================
val Maroon = Color(0xFF7B0F1A)
val MaroonDark = Color(0xFF5E0A13)
val MaroonLight = Color(0xFF9E1B28)
val SosRed = Color(0xFFB3141F)
val GreenSafe = Color(0xFF2E9E4F)
val White = Color(0xFFFFFFFF)
val GoldBadge = Color(0xFFF2D7A6)
val Amber = Color(0xFFB7791F)
val BadgeRed = Color(0xFFFF5A5F)

val AlertStart = Color(0xFF8E0E19)
val AlertEnd = Color(0xFFD1222F)
val HeroStart = Color(0xFF4A0710)
val HeroEnd = Color(0xFF8A1424)

// =========================================================================
// STATIC PALET WARNA TERANG (LIGHT)
// =========================================================================
val LightCream = Color(0xFFFFF4EC)
val LightCardBg = Color(0xFFFFFBF8)
val LightPeach = Color(0xFFF3C9BB)
val LightStroke = Color(0xFFEAD7CC)
val LightTextDark = Color(0xFF3A0A10)
val LightTextGray = Color(0xFF7A6A6A)
val LightTextPrimary = Color(0xFF54131B)
val LightTextSecondary = Color(0xFF8A6E68)
val LightSoftRed = Color(0xFFFDE8E8)
val LightSoftGreen = Color(0xFFE6F6EC)
val LightSoftAmber = Color(0xFFFFF1DC)
val LightSoftGray = Color(0xFFF4EEE9)
val LightSoftCream = Color(0xFFFBF4EE)
val LightSoftPeach = Color(0xFFF9E6E1)

// =========================================================================
// STATIC PALET WARNA GELAP (DARK)
// =========================================================================
val DarkBackground = Color(0xFF1A1112)
val DarkSurface = Color(0xFF24191A)
val DarkCard = Color(0xFF2C2022)
val DarkTextPrimary = Color(0xFFF8EBEB)
val DarkTextSecondary = Color(0xFFB8A6A7)
val DarkStroke = Color(0xFF422E30)
val DarkPeach = Color(0xFF5E0A13) // MaroonDark
val DarkSoftPeach = Color(0xFF2C2022)
val DarkSoftRed = Color(0xFF2C1A1A)
val DarkSoftGreen = Color(0xFF1A2E20)

// =========================================================================
// DYNAMIC COLOR ACCESSORS (Sesuai Mode Terang / Gelap / Sistem Handphone)
// Memastikan semua Composable yang memanggil Cream, CardBg, TextDark, dll.
// secara otomatis dan instan berubah warnanya tanpa perlu refactor ratusan file!
// =========================================================================
val Cream: Color
    @Composable
    @ReadOnlyComposable
    get() = if (LocalAppColors.current.isDark) DarkBackground else LightCream

val CardBg: Color
    @Composable
    @ReadOnlyComposable
    get() = if (LocalAppColors.current.isDark) DarkCard else LightCardBg

val Peach: Color
    @Composable
    @ReadOnlyComposable
    get() = if (LocalAppColors.current.isDark) DarkPeach else LightPeach

val StrokeColor: Color
    @Composable
    @ReadOnlyComposable
    get() = if (LocalAppColors.current.isDark) DarkStroke else LightStroke

val TextDark: Color
    @Composable
    @ReadOnlyComposable
    get() = if (LocalAppColors.current.isDark) DarkTextPrimary else LightTextDark

val TextGray: Color
    @Composable
    @ReadOnlyComposable
    get() = if (LocalAppColors.current.isDark) DarkTextSecondary else LightTextGray

val TextPrimary: Color
    @Composable
    @ReadOnlyComposable
    get() = if (LocalAppColors.current.isDark) DarkTextPrimary else LightTextPrimary

val TextSecondary: Color
    @Composable
    @ReadOnlyComposable
    get() = if (LocalAppColors.current.isDark) DarkTextSecondary else LightTextSecondary

val SoftRed: Color
    @Composable
    @ReadOnlyComposable
    get() = if (LocalAppColors.current.isDark) DarkSoftRed else LightSoftRed

val SoftGreen: Color
    @Composable
    @ReadOnlyComposable
    get() = if (LocalAppColors.current.isDark) DarkSoftGreen else LightSoftGreen

val SoftCream: Color
    @Composable
    @ReadOnlyComposable
    get() = if (LocalAppColors.current.isDark) DarkSurface else LightSoftCream

val SoftPeach: Color
    @Composable
    @ReadOnlyComposable
    get() = if (LocalAppColors.current.isDark) DarkSoftPeach else LightSoftPeach

val SoftGray: Color
    @Composable
    @ReadOnlyComposable
    get() = if (LocalAppColors.current.isDark) DarkSurface else LightSoftGray

val SoftAmber: Color
    @Composable
    @ReadOnlyComposable
    get() = if (LocalAppColors.current.isDark) DarkCard else LightSoftAmber
