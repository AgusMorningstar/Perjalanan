package com.example.perjalanan.ui.screens.emergency

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class EmergencyUiState(
    val emergencyNumber: String = "110",
    val policeUnitInfo: String = "Layanan Polisi Bebas Pulsa 24 Jam",
    val description: String = "Tekan tombol panggil untuk segera terhubung dengan petugas kepolisian terdekat dari koordinat Anda."
)

class EmergencyViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(EmergencyUiState())
    val uiState: StateFlow<EmergencyUiState> = _uiState.asStateFlow()
}
