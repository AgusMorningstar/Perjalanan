package com.example.perjalanan.data.model

data class HealthMetrics(
    val heartRate: Int = 72,
    val heartRateState: String = "Normal",
    val motionState: String = "Berjalan Santai",
    val motionSpeed: Double = 1.2,
    val motionUnit: String = "m/s",
    val batteryPercent: Int = 85,
    val isSmartwatchConnected: Boolean = true,
    val isGpsActive: Boolean = true,
    val gpsAccuracy: String = "Tinggi (±3m)",
    val securityStatus: String = "Aman",
    val lastSyncTime: String = "Baru saja"
)
