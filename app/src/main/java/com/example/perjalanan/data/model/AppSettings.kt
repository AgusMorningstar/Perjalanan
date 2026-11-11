package com.example.perjalanan.data.model

enum class ThemeMode {
    LIGHT, DARK, SYSTEM
}

enum class Language {
    INDONESIA, ENGLISH
}

data class AppSettings(
    val themeMode: ThemeMode = ThemeMode.LIGHT,
    val language: Language = Language.INDONESIA
)
