package com.example.perjalanan.ui.screens.police

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.perjalanan.data.AppContainer
import com.example.perjalanan.data.model.PoliceOfficer
import com.example.perjalanan.data.model.SosReport
import com.example.perjalanan.data.repository.PoliceRepository
import com.example.perjalanan.data.repository.SosRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class PoliceDashboardUiState(
    val officer: PoliceOfficer = PoliceOfficer(),
    val reports: List<SosReport> = emptyList(),
    val actionMessage: String? = null
)

class PoliceDashboardViewModel(
    private val policeRepository: PoliceRepository = AppContainer.policeRepository,
    private val sosRepository: SosRepository = AppContainer.sosRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PoliceDashboardUiState())
    val uiState: StateFlow<PoliceDashboardUiState> = _uiState.asStateFlow()

    init {
        observeData()
    }

    private fun observeData() {
        viewModelScope.launch {
            policeRepository.getOfficerProfile().collect { officer ->
                _uiState.update { it.copy(officer = officer) }
            }
        }
        viewModelScope.launch {
            sosRepository.getSosReports().collect { reports ->
                _uiState.update { it.copy(reports = reports) }
            }
        }
    }

    fun onVolumeChange(newVolume: Int) {
        viewModelScope.launch {
            policeRepository.setAlarmVolume(newVolume)
        }
    }

    fun stopAlarm() {
        viewModelScope.launch {
            policeRepository.setAlarmActive(false)
            _uiState.update { it.copy(actionMessage = "Alarm berhasil dimatikan") }
        }
    }

    fun toggleAlarm(isActive: Boolean) {
        viewModelScope.launch {
            policeRepository.setAlarmActive(isActive)
            _uiState.update {
                it.copy(actionMessage = if (isActive) "Alarm SOS diaktifkan" else "Alarm SOS dimatikan")
            }
        }
    }

    fun acceptReport(reportId: String) {
        viewModelScope.launch {
            sosRepository.acceptReport(reportId)
            _uiState.update { it.copy(actionMessage = "Laporan $reportId diterima oleh petugas!") }
        }
    }

    fun clearActionMessage() {
        _uiState.update { it.copy(actionMessage = null) }
    }
}
