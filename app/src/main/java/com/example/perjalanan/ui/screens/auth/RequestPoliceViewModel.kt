package com.example.perjalanan.ui.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.perjalanan.data.AppContainer
import com.example.perjalanan.data.repository.PoliceRegistrationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class RequestPoliceUiState(
    val polsekName: String  = "",
    val fullName:   String  = "",
    val nrp:        String  = "",
    val phone:      String  = "",
    val reason:     String  = "",
    val isLoading:  Boolean = false,
    val errorMessage: String? = null,
    val isSuccess:  Boolean = false,
    val successMessage: String = ""
)

class RequestPoliceViewModel(
    private val repository: PoliceRegistrationRepository = AppContainer.policeRegistrationRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RequestPoliceUiState())
    val uiState: StateFlow<RequestPoliceUiState> = _uiState.asStateFlow()

    fun onPolsekNameChange(v: String) = _uiState.update { it.copy(polsekName = v, errorMessage = null) }
    fun onFullNameChange(v: String)   = _uiState.update { it.copy(fullName   = v, errorMessage = null) }
    fun onNrpChange(v: String)        = _uiState.update { it.copy(nrp        = v, errorMessage = null) }
    fun onPhoneChange(v: String)      = _uiState.update { it.copy(phone      = v, errorMessage = null) }
    fun onReasonChange(v: String)     = _uiState.update { it.copy(reason     = v, errorMessage = null) }

    fun submitRequest() {
        val state = _uiState.value

        // Validasi lokal
        if (state.polsekName.isBlank()) {
            _uiState.update { it.copy(errorMessage = "Nama polsek wajib diisi") }
            return
        }
        if (state.fullName.trim().length < 3) {
            _uiState.update { it.copy(errorMessage = "Nama lengkap minimal 3 karakter") }
            return
        }
        if (state.nrp.trim().length < 4) {
            _uiState.update { it.copy(errorMessage = "NRP/NIP tidak valid (minimal 4 karakter)") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            val result = repository.submitRequest(
                polsekName = state.polsekName,
                fullName   = state.fullName,
                nrp        = state.nrp,
                phone      = state.phone.ifBlank { null },
                reason     = state.reason.ifBlank { null }
            )
            result.onSuccess { message ->
                _uiState.update { it.copy(isLoading = false, isSuccess = true, successMessage = message) }
            }.onFailure { err ->
                _uiState.update { it.copy(isLoading = false, errorMessage = err.message ?: "Gagal mengirim request") }
            }
        }
    }
}
