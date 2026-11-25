package com.example.perjalanan.ui.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.perjalanan.data.AppContainer
import com.example.perjalanan.data.model.User
import com.example.perjalanan.data.repository.AuthRepository
import com.example.perjalanan.ui.components.EditableFieldType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ProfileUiState(
    val user: User = User(),
    val isLogoutDialogOpen: Boolean = false,
    val isLoggedOut: Boolean = false,
    val editingField: EditableFieldType? = null,
    val isChangePasswordDialogOpen: Boolean = false,
    val isDialogLoading: Boolean = false,
    val dialogErrorMessage: String? = null,
    val snackbarMessage: String? = null
)

class ProfileViewModel(
    private val authRepository: AuthRepository = AppContainer.authRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        loadUser()
    }

    private fun loadUser() {
        viewModelScope.launch {
            authRepository.currentUser.collect { user ->
                if (user != null) {
                    _uiState.update { it.copy(user = user) }
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
                        user = updatedUser,
                        editingField = null,
                        isDialogLoading = false,
                        dialogErrorMessage = null,
                        snackbarMessage = "Informasi akun berhasil diperbarui"
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
                        snackbarMessage = "Kata sandi berhasil diperbarui"
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

    fun clearSnackbar() {
        _uiState.update { it.copy(snackbarMessage = null) }
    }

    fun openLogoutDialog() {
        _uiState.update { it.copy(isLogoutDialogOpen = true) }
    }

    fun closeLogoutDialog() {
        _uiState.update { it.copy(isLogoutDialogOpen = false) }
    }

    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
            _uiState.update { it.copy(isLogoutDialogOpen = false, isLoggedOut = true) }
        }
    }
}
