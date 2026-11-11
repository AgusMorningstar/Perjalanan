package com.example.perjalanan.data.repository

import com.example.perjalanan.data.model.HealthMetrics
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

interface HealthRepository {
    fun getHealthMetrics(): Flow<HealthMetrics>
    suspend fun updateHeartRate(newRate: Int)
}

class HealthRepositoryImpl : HealthRepository {
    private val _metrics = MutableStateFlow(
        HealthMetrics(
            heartRate = 72,
            heartRateState = "Normal",
            motionState = "Berjalan Santai",
            motionSpeed = 1.2,
            batteryPercent = 85,
            isSmartwatchConnected = true,
            isGpsActive = true,
            gpsAccuracy = "Tinggi (±3m)",
            securityStatus = "Aman",
            lastSyncTime = "Baru saja"
        )
    )

    override fun getHealthMetrics(): Flow<HealthMetrics> = _metrics.asStateFlow()

    override suspend fun updateHeartRate(newRate: Int) {
        _metrics.update {
            it.copy(
                heartRate = newRate,
                heartRateState = if (newRate > 100) "Tinggi" else if (newRate < 60) "Rendah" else "Normal"
            )
        }
    }
}
