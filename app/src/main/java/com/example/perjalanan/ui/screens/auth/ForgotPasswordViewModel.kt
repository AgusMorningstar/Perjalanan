package com.example.perjalanan.ui.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.perjalanan.data.AppContainer
import com.example.perjalanan.data.remote.dto.ForgotPasswordResponse
import com.example.perjalanan.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ForgotPasswordUiState(
    val identifier: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val successResponse: ForgotPasswordResponse? = null
)

class ForgotPasswordViewModel(
    private val authRepository: AuthRepository = AppContainer.authRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ForgotPasswordUiState())
    val uiState: StateFlow<ForgotPasswordUiState> = _uiState.asStateFlow()

    fun onIdentifierChange(value: String) {
        _uiState.update { it.copy(identifier = value, errorMessage = null) }
    }

    fun requestOtp() {
        val currentIdentifier = _uiState.value.identifier.trim()
        if (currentIdentifier.isBlank()) {
            _uiState.update { it.copy(errorMessage = "Masukkan alamat email akun Anda") }
            return
        }
        if (!currentIdentifier.contains("@") || !currentIdentifier.contains(".")) {
            _uiState.update { it.copy(errorMessage = "Format alamat email tidak valid") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            val result = authRepository.requestForgotPasswordOtp(currentIdentifier)
            result.onSuccess { response ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        successResponse = response
                    )
                }
            }.onFailure { error ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = error.message ?: "Gagal mengirim kode OTP ke email"
                    )
                }
            }
        }
    }

    fun resetState() {
        _uiState.update { it.copy(errorMessage = null, successResponse = null) }
    }
}
