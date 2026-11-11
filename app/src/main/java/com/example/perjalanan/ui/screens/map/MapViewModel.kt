package com.example.perjalanan.ui.screens.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.perjalanan.data.AppContainer
import com.example.perjalanan.data.model.SosReport
import com.example.perjalanan.data.repository.SosRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class MapUiState(
    val currentVictimReport: SosReport? = null,
    val victimLat: Double = -5.1477,
    val victimLng: Double = 119.4327,
    val victimAddress: String = "Jl. Urip Sumoharjo, Makassar",
    val victimName: String = "Salsabilaa",
    val radiusMeter: Double = 150.0,
    val isLoading: Boolean = false
)

class MapViewModel(
    private val sosRepository: SosRepository = AppContainer.sosRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MapUiState())
    val uiState: StateFlow<MapUiState> = _uiState.asStateFlow()

    init {
        loadVictimData()
    }

    private fun loadVictimData() {
        viewModelScope.launch {
            sosRepository.getSosReports().collect { reports ->
                val firstEmergency = reports.firstOrNull { it.isEmergency }
                if (firstEmergency != null) {
                    _uiState.update {
                        it.copy(
                            currentVictimReport = firstEmergency,
                            victimLat = firstEmergency.latitude,
                            victimLng = firstEmergency.longitude,
                            victimAddress = firstEmergency.locationAddress,
                            victimName = firstEmergency.victimName
                        )
                    }
                }
            }
        }
    }
}
