package com.example.perjalanan.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.perjalanan.data.AppContainer
import com.example.perjalanan.data.model.AppSettings
import com.example.perjalanan.data.model.Language
import com.example.perjalanan.data.model.ThemeMode
import com.example.perjalanan.data.repository.SettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class SettingsUiState(
    val settings: AppSettings = AppSettings(),
    val isThemeDialogVisible: Boolean = false,
    val isLanguageDialogVisible: Boolean = false
)

class SettingsViewModel(
    private val settingsRepository: SettingsRepository = AppContainer.settingsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    init {
        observeSettings()
    }

    private fun observeSettings() {
        viewModelScope.launch {
            settingsRepository.getSettings().collect { s ->
                _uiState.update { it.copy(settings = s) }
            }
        }
    }

    fun openThemeDialog() = _uiState.update { it.copy(isThemeDialogVisible = true) }
    fun closeThemeDialog() = _uiState.update { it.copy(isThemeDialogVisible = false) }

    fun openLanguageDialog() = _uiState.update { it.copy(isLanguageDialogVisible = true) }
    fun closeLanguageDialog() = _uiState.update { it.copy(isLanguageDialogVisible = false) }

    fun selectTheme(mode: ThemeMode) {
        viewModelScope.launch {
            settingsRepository.setThemeMode(mode)
            closeThemeDialog()
        }
    }

    fun selectLanguage(lang: Language) {
        viewModelScope.launch {
            settingsRepository.setLanguage(lang)
            closeLanguageDialog()
        }
    }
}
