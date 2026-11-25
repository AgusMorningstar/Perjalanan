package com.example.perjalanan.ui.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.perjalanan.data.AppContainer
import com.example.perjalanan.data.repository.AuthRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class VerifyCodeUiState(
    val otp: List<String> = listOf("", "", "", ""),
    val secondsRemaining: Int = 59,
    val isTimerActive: Boolean = true,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val resetToken: String? = null,
    val isSuccess: Boolean = false
)

class VerifyCodeViewModel(
    private val authRepository: AuthRepository = AppContainer.authRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(VerifyCodeUiState())
    val uiState: StateFlow<VerifyCodeUiState> = _uiState.asStateFlow()

    private var timerJob: Job? = null

    init {
        startTimer()
    }

    fun onOtpDigitChange(index: Int, value: String) {
        val currentOtp = _uiState.value.otp.toMutableList()
        currentOtp[index] = value.take(1)
        _uiState.update { it.copy(otp = currentOtp, errorMessage = null) }
    }

    fun startTimer() {
        timerJob?.cancel()
        _uiState.update { it.copy(secondsRemaining = 59, isTimerActive = true) }
        timerJob = viewModelScope.launch {
            for (sec in 58 downTo 0) {
                delay(1000)
                _uiState.update { it.copy(secondsRemaining = sec) }
            }
            _uiState.update { it.copy(isTimerActive = false) }
        }
    }

    fun resend(identifier: String) {
        if (_uiState.value.isTimerActive || identifier.isBlank()) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            val result = authRepository.requestForgotPasswordOtp(identifier)
            result.onSuccess {
                _uiState.update { it.copy(isLoading = false) }
                startTimer()
            }.onFailure { err ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = err.message ?: "Gagal mengirim ulang kode"
                    )
                }
            }
        }
    }

    fun verify(identifier: String) {
        val code = _uiState.value.otp.joinToString("")
        if (code.length < 4) {
            _uiState.update { it.copy(errorMessage = "Lengkapi 4 digit kode verifikasi") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            // Jika identifier ada (dari flow Lupa Password online), gunakan API verifyOtp
            if (identifier.isNotBlank()) {
                val result = authRepository.verifyOtp(identifier, code)
                result.onSuccess { token ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            resetToken = token,
                            isSuccess = true
                        )
                    }
                }.onFailure { err ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = err.message ?: "Verifikasi kode OTP gagal"
                        )
                    }
                }
            } else {
                // Fallback verifikasi lokal jika dipanggil tanpa identifier
                val result = authRepository.verifyCode(code)
                result.onSuccess {
                    _uiState.update { it.copy(isLoading = false, isSuccess = true) }
                }.onFailure { err ->
                    _uiState.update { it.copy(isLoading = false, errorMessage = err.message) }
                }
            }
        }
    }

    fun resetState() {
        _uiState.update { it.copy(isSuccess = false, errorMessage = null) }
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }
}
