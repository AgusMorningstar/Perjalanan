package com.example.perjalanan.data.repository

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
}

class SettingsRepositoryImpl : SettingsRepository {
    private val _settings = MutableStateFlow(AppSettings())

    override fun getSettings(): Flow<AppSettings> = _settings.asStateFlow()

    override suspend fun setThemeMode(mode: ThemeMode) {
        _settings.update { it.copy(themeMode = mode) }
    }

    override suspend fun setLanguage(language: Language) {
        _settings.update { it.copy(language = language) }
    }
}
