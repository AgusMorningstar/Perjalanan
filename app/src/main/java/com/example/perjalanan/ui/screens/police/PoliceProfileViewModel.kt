package com.example.perjalanan.ui.screens.police

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.perjalanan.data.AppContainer
import com.example.perjalanan.data.model.PoliceOfficer
import com.example.perjalanan.data.repository.AuthRepository
import com.example.perjalanan.data.repository.PoliceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class PoliceProfileUiState(
    val officer: PoliceOfficer = PoliceOfficer(),
    val isAlarmSettingsOpen: Boolean = false,
    val isLogoutDialogOpen: Boolean = false,
    val isLoggedOut: Boolean = false,
    val toastMessage: String? = null
)

class PoliceProfileViewModel(
    private val policeRepository: PoliceRepository = AppContainer.policeRepository,
    private val authRepository: AuthRepository = AppContainer.authRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PoliceProfileUiState())
    val uiState: StateFlow<PoliceProfileUiState> = _uiState.asStateFlow()

    init {
        observeData()
    }

    private fun observeData() {
        viewModelScope.launch {
            policeRepository.getOfficerProfile().collect { officer ->
                _uiState.update { it.copy(officer = officer) }
            }
        }
    }

    fun openAlarmSettings() = _uiState.update { it.copy(isAlarmSettingsOpen = true) }
    fun closeAlarmSettings() = _uiState.update { it.copy(isAlarmSettingsOpen = false) }

    fun openLogoutDialog() = _uiState.update { it.copy(isLogoutDialogOpen = true) }
    fun closeLogoutDialog() = _uiState.update { it.copy(isLogoutDialogOpen = false) }

    fun setAlarm(isActive: Boolean) {
        viewModelScope.launch {
            policeRepository.setAlarmActive(isActive)
            closeAlarmSettings()
            _uiState.update {
                it.copy(toastMessage = if (isActive) "Alarm berhasil diaktifkan" else "Alarm dinonaktifkan")
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
            _uiState.update { it.copy(isLogoutDialogOpen = false, isLoggedOut = true) }
        }
    }

    fun clearToast() {
        _uiState.update { it.copy(toastMessage = null) }
    }
}
