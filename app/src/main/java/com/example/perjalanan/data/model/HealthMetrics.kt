package com.example.perjalanan.data.model

data class HealthMetrics(
    val heartRate: Int = 0,
    val heartRateState: String = "Menunggu Sensor",
    val motionState: String = "Diam",
    val motionSpeed: Double = 0.0,
    val motionUnit: String = "m/s",
    val batteryPercent: Int = 100,
    val isUsbPowered: Boolean = true,
    val batteryLabel: String = "Daya USB ⚡",
    val isSmartwatchConnected: Boolean = false,
    val isGpsActive: Boolean = true,
    val gpsAccuracy: String = "Tinggi (±3m)",
    val securityStatus: String = "Aman",
    val lastSyncTime: String = "Belum Terhubung",
    val mpuData: MpuSensorData = MpuSensorData()
)
