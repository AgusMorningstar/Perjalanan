package com.example.perjalanan.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.perjalanan.data.AppContainer
import com.example.perjalanan.data.model.HealthMetrics
import com.example.perjalanan.data.model.User
import com.example.perjalanan.data.repository.AuthRepository
import com.example.perjalanan.data.repository.HealthRepository
import com.example.perjalanan.data.repository.SosRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class HomeUiState(
    val user: User = User(),
    val healthMetrics: HealthMetrics = HealthMetrics(),
    val isSosDialogOpen: Boolean = false,
    val isSendingSos: Boolean = false,
    val sosSentMessage: String? = null
)

class HomeViewModel(
    private val authRepository: AuthRepository = AppContainer.authRepository,
    private val healthRepository: HealthRepository = AppContainer.healthRepository,
    private val sosRepository: SosRepository = AppContainer.sosRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        observeData()
    }

    private fun observeData() {
        viewModelScope.launch {
            authRepository.currentUser.collect { currentUser ->
                if (currentUser != null) {
                    _uiState.update { it.copy(user = currentUser) }
                }
            }
        }
        viewModelScope.launch {
            healthRepository.getHealthMetrics().collect { metrics ->
                _uiState.update { it.copy(healthMetrics = metrics) }
            }
        }
    }

    fun openSosDialog() {
        _uiState.update { it.copy(isSosDialogOpen = true) }
    }

    fun closeSosDialog() {
        _uiState.update { it.copy(isSosDialogOpen = false) }
    }

    fun sendSos() {
        viewModelScope.launch {
            _uiState.update { it.copy(isSosDialogOpen = false, isSendingSos = true) }
            val state = _uiState.value
            val result = sosRepository.triggerSos(
                victimName = state.user.name,
                lat = -5.1477,
                lng = 119.4327,
                address = "Jl. Urip Sumoharjo, Makassar"
            )
            result.onSuccess {
                _uiState.update {
                    it.copy(
                        isSendingSos = false,
                        sosSentMessage = "Sinyal SOS darurat berhasil dikirim ke Kepolisian!"
                    )
                }
            }.onFailure { err ->
                _uiState.update {
                    it.copy(
                        isSendingSos = false,
                        sosSentMessage = "Gagal mengirim SOS: ${err.message}"
                    )
                }
            }
        }
    }

    fun clearSosMessage() {
        _uiState.update { it.copy(sosSentMessage = null) }
    }
}
