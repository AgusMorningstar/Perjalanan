package com.example.perjalanan.data.repository

import android.content.Context
import com.example.perjalanan.App
import com.example.perjalanan.data.model.AppSettings
import com.example.perjalanan.data.model.Language
import com.example.perjalanan.data.model.ThemeMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

interface SettingsRepository {
    fun getSettings(): Flow<AppSettings>
    suspend fun setThemeMode(mode: ThemeMode)
    suspend fun setLanguage(language: Language)
    suspend fun setOnboardingCompleted(completed: Boolean)
}

class SettingsRepositoryImpl : SettingsRepository {

    private val prefs by lazy {
        try {
            App.instance.getSharedPreferences("app_settings_pref", Context.MODE_PRIVATE)
        } catch (e: Exception) {
            null
        }
    }

    private val _settings: MutableStateFlow<AppSettings>

    init {
        val savedThemeStr = prefs?.getString("theme_mode", ThemeMode.LIGHT.name) ?: ThemeMode.LIGHT.name
        val savedLangStr = prefs?.getString("language", Language.INDONESIA.name) ?: Language.INDONESIA.name
        val savedOnboarding = prefs?.getBoolean("onboarding_completed", false) ?: false

        val initialTheme = try { ThemeMode.valueOf(savedThemeStr) } catch (e: Exception) { ThemeMode.LIGHT }
        val initialLang = try { Language.valueOf(savedLangStr) } catch (e: Exception) { Language.INDONESIA }

        _settings = MutableStateFlow(
            AppSettings(
                themeMode = initialTheme,
                language = initialLang,
                isOnboardingCompleted = savedOnboarding
            )
        )
    }

    override fun getSettings(): Flow<AppSettings> = _settings.asStateFlow()

    override suspend fun setThemeMode(mode: ThemeMode) {
        prefs?.edit()?.putString("theme_mode", mode.name)?.apply()
        _settings.update { it.copy(themeMode = mode) }
    }

    override suspend fun setLanguage(language: Language) {
        prefs?.edit()?.putString("language", language.name)?.apply()
        _settings.update { it.copy(language = language) }
    }

    override suspend fun setOnboardingCompleted(completed: Boolean) {
        prefs?.edit()?.putBoolean("onboarding_completed", completed)?.apply()
        _settings.update { it.copy(isOnboardingCompleted = completed) }
    }
}
