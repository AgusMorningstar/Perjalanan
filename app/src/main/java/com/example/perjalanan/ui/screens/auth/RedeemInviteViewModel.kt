package com.example.perjalanan.ui.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.perjalanan.data.AppContainer
import com.example.perjalanan.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class RedeemInviteUiState(
    val code: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isSuccess: Boolean = false
)

class RedeemInviteViewModel(
    private val authRepository: AuthRepository = AppContainer.authRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RedeemInviteUiState())
    val uiState: StateFlow<RedeemInviteUiState> = _uiState.asStateFlow()

    fun onCodeChange(newCode: String) {
        // Hanya huruf dan angka tanpa tanda strip (-), otomatis uppercase
        val filtered = newCode.filter { it.isLetterOrDigit() }.uppercase().take(16)
        _uiState.update { it.copy(code = filtered, errorMessage = null) }
    }

    fun redeemCode() {
        val code = _uiState.value.code.filter { it.isLetterOrDigit() }.uppercase()
        if (code.isBlank()) {
            _uiState.update { it.copy(errorMessage = "Kode undangan tidak boleh kosong") }
            return
        }
        if (code.length < 6) {
            _uiState.update { it.copy(errorMessage = "Kode undangan minimal 6 karakter") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            val result = authRepository.redeemInvite(code)
            result.onSuccess {
                _uiState.update { it.copy(isLoading = false, isSuccess = true) }
            }.onFailure { err ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = err.message ?: "Gagal menukar kode undangan"
                    )
                }
            }
        }
    }

    fun resetState() {
        _uiState.update { it.copy(isSuccess = false, errorMessage = null) }
    }
}
