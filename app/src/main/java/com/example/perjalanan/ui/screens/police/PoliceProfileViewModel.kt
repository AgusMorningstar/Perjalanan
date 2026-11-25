package com.example.perjalanan.ui.screens.police

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.perjalanan.data.AppContainer
import com.example.perjalanan.data.model.PoliceOfficer
import com.example.perjalanan.data.repository.AuthRepository
import com.example.perjalanan.data.repository.PoliceRepository
import com.example.perjalanan.ui.components.EditableFieldType
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
    val toastMessage: String? = null,
    val editingField: EditableFieldType? = null,
    val isChangePasswordDialogOpen: Boolean = false,
    val isDialogLoading: Boolean = false,
    val dialogErrorMessage: String? = null
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
        // Observasi status alarm & tugas dari policeRepository
        viewModelScope.launch {
            policeRepository.getOfficerProfile().collect { officer ->
                _uiState.update { current ->
                    current.copy(
                        officer = current.officer.copy(
                            isDutyActive = officer.isDutyActive,
                            dutyStatusText = officer.dutyStatusText,
                            isAlarmActive = officer.isAlarmActive,
                            isAlarmConnected = officer.isAlarmConnected,
                            alarmVolume = officer.alarmVolume,
                            activeReportsCount = officer.activeReportsCount,
                            resolvedReportsCount = officer.resolvedReportsCount,
                            availableUnitsCount = officer.availableUnitsCount
                        )
                    )
                }
            }
        }

        // Observasi data akun nyata (Nama, Email, Telepon, Polsek) dari authRepository
        viewModelScope.launch {
            authRepository.currentUser.collect { user ->
                if (user != null) {
                    _uiState.update { current ->
                        current.copy(
                            officer = current.officer.copy(
                                id = user.id.take(8).uppercase(),
                                name = user.name.ifBlank { current.officer.name },
                                email = user.email.ifBlank { current.officer.email },
                                phone = user.phone.ifBlank { current.officer.phone },
                                unit = user.polsekName ?: "Polsek Sektor Terdaftar",
                                office = user.polsekName ?: "Kantor Kepolisian Sektor"
                            )
                        )
                    }
                }
            }
        }
    }

    fun openEditFieldDialog(fieldType: EditableFieldType) {
        _uiState.update {
            it.copy(
                editingField = fieldType,
                dialogErrorMessage = null,
                isDialogLoading = false
            )
        }
    }

    fun closeEditFieldDialog() {
        _uiState.update {
            it.copy(
                editingField = null,
                dialogErrorMessage = null,
                isDialogLoading = false
            )
        }
    }

    fun openChangePasswordDialog() {
        _uiState.update {
            it.copy(
                isChangePasswordDialogOpen = true,
                dialogErrorMessage = null,
                isDialogLoading = false
            )
        }
    }

    fun closeChangePasswordDialog() {
        _uiState.update {
            it.copy(
                isChangePasswordDialogOpen = false,
                dialogErrorMessage = null,
                isDialogLoading = false
            )
        }
    }

    fun submitFieldUpdate(newValue: String, currentPassword: String) {
        val field = _uiState.value.editingField ?: return
        viewModelScope.launch {
            _uiState.update { it.copy(isDialogLoading = true, dialogErrorMessage = null) }

            val result = when (field) {
                EditableFieldType.NAME -> authRepository.updateProfile(username = newValue, currentPassword = currentPassword)
                EditableFieldType.EMAIL -> authRepository.updateProfile(email = newValue, currentPassword = currentPassword)
                EditableFieldType.PHONE -> authRepository.updateProfile(phone = newValue, currentPassword = currentPassword)
            }

            result.onSuccess { updatedUser ->
                _uiState.update {
                    it.copy(
                        editingField = null,
                        isDialogLoading = false,
                        dialogErrorMessage = null,
                        toastMessage = "Informasi akun berhasil diperbarui",
                        officer = it.officer.copy(
                            name = updatedUser.name,
                            email = updatedUser.email,
                            phone = updatedUser.phone
                        )
                    )
                }
            }.onFailure { err ->
                _uiState.update {
                    it.copy(
                        isDialogLoading = false,
                        dialogErrorMessage = err.message ?: "Gagal memperbarui informasi akun"
                    )
                }
            }
        }
    }

    fun submitPasswordChange(currentPassword: String, newPassword: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isDialogLoading = true, dialogErrorMessage = null) }

            val result = authRepository.changePasswordWithOld(currentPassword, newPassword)
            result.onSuccess {
                _uiState.update {
                    it.copy(
                        isChangePasswordDialogOpen = false,
                        isDialogLoading = false,
                        dialogErrorMessage = null,
                        toastMessage = "Kata sandi berhasil diperbarui"
                    )
                }
            }.onFailure { err ->
                _uiState.update {
                    it.copy(
                        isDialogLoading = false,
                        dialogErrorMessage = err.message ?: "Gagal memperbarui kata sandi"
                    )
                }
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
