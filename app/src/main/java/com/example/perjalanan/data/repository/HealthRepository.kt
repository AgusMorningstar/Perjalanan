package com.example.perjalanan.data.repository

import com.example.perjalanan.App
import com.example.perjalanan.data.ble.BleConnectionState
import com.example.perjalanan.data.ble.BleManager
import com.example.perjalanan.data.ble.DiscoveredBleDevice
import com.example.perjalanan.data.model.HealthMetrics
import com.example.perjalanan.data.model.MpuSensorData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

interface HealthRepository {
    fun getHealthMetrics(): Flow<HealthMetrics>
    val bleState: StateFlow<BleConnectionState>
    val connectedDeviceName: StateFlow<String?>
    val discoveredDevices: StateFlow<List<DiscoveredBleDevice>>
    val latestTelemetry: StateFlow<String>
    val bleErrorMessage: StateFlow<String?>
    val mpuData: StateFlow<MpuSensorData>
    val lastSavedDevice: StateFlow<DiscoveredBleDevice?>

    fun startBleScan()
    fun stopBleScan()
    fun connectBleDevice(address: String)
    fun disconnectBleDevice()
    suspend fun updateHeartRate(newRate: Int)
}

class HealthRepositoryImpl(
    private val bleManager: BleManager = BleManager(App.instance)
) : HealthRepository {

    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    private val _metrics = MutableStateFlow(
        HealthMetrics(
            heartRate = 0,
            heartRateState = "Menunggu Sensor",
            motionState = "Diam",
            motionSpeed = 0.0,
            batteryPercent = 100,
            isUsbPowered = true,
            batteryLabel = "Daya USB ⚡",
            isSmartwatchConnected = false,
            isGpsActive = true,
            gpsAccuracy = "Tinggi (±3m)",
            securityStatus = "Aman",
            lastSyncTime = "Belum Terhubung"
        )
    )

    override fun getHealthMetrics(): Flow<HealthMetrics> = _metrics.asStateFlow()

    override val bleState: StateFlow<BleConnectionState> = bleManager.connectionState
    override val connectedDeviceName: StateFlow<String?> = bleManager.connectedDeviceName
    override val discoveredDevices: StateFlow<List<DiscoveredBleDevice>> = bleManager.discoveredDevices
    override val latestTelemetry: StateFlow<String> = bleManager.latestTelemetry
    override val bleErrorMessage: StateFlow<String?> = bleManager.errorMessage
    override val mpuData: StateFlow<MpuSensorData> = bleManager.mpuData
    override val lastSavedDevice: StateFlow<DiscoveredBleDevice?> = bleManager.lastSavedDevice

    init {
        observeBleEvents()
    }

    private fun observeBleEvents() {
        // Observe connection state
        scope.launch {
            bleManager.connectionState.collect { state ->
                val isConnected = state == BleConnectionState.CONNECTED
                _metrics.update {
                    it.copy(
                        isSmartwatchConnected = isConnected,
                        lastSyncTime = if (isConnected) "Terkoneksi ke ${bleManager.connectedDeviceName.value ?: BleManager.TARGET_DEVICE_NAME}" else "Terputus"
                    )
                }
            }
        }

        // Observe real-time Heart Rate from ESP32
        scope.launch {
            bleManager.heartRateBpm.collect { bpm ->
                _metrics.update { current ->
                    if (bpm > 0) {
                        current.copy(
                            heartRate = bpm,
                            heartRateState = when {
                                bpm > 110 -> "Tinggi (Panik/Darurat)"
                                bpm < 50 -> "Rendah"
                                else -> "Normal"
                            }
                        )
                    } else if (current.isSmartwatchConnected) {
                        current.copy(
                            heartRate = 0,
                            heartRateState = if (current.mpuData.isMoving)
                                "Gerakan terdeteksi (Tahan diam)"
                            else
                                "Mengukur / Kalibrasi..."
                        )
                    } else {
                        current.copy(heartRate = 0, heartRateState = "Menunggu Sensor")
                    }
                }
            }
        }

        // Observe MPU6050 6-DOF and Motion data
        scope.launch {
            bleManager.mpuData.collect { mpu ->
                _metrics.update { current ->
                    current.copy(
                        mpuData = mpu,
                        motionState = mpu.motionLabel,
                        motionSpeed = if (mpu.isMoving) 1.2 else 0.0
                    )
                }
            }
        }

        // Observe Battery status
        scope.launch {
            bleManager.batteryPercent.collect { percent ->
                val isUsb = bleManager.isUsbPowered.value
                _metrics.update {
                    it.copy(
                        batteryPercent = percent,
                        isUsbPowered = isUsb,
                        batteryLabel = if (isUsb) "Daya USB ⚡" else "$percent%"
                    )
                }
            }
        }
    }

    override fun startBleScan() {
        bleManager.startScan()
    }

    override fun stopBleScan() {
        bleManager.stopScan()
    }

    override fun connectBleDevice(address: String) {
        bleManager.connect(address)
    }

    override fun disconnectBleDevice() {
        bleManager.disconnect()
    }

    override suspend fun updateHeartRate(newRate: Int) {
        _metrics.update {
            it.copy(
                heartRate = newRate,
                heartRateState = if (newRate > 100) "Tinggi" else if (newRate < 60) "Rendah" else "Normal"
            )
        }
    }
}
