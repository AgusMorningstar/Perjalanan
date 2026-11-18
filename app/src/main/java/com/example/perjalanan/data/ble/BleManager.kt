package com.example.perjalanan.data.ble

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattDescriptor
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothProfile
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.content.Context
import android.location.LocationManager
import android.os.Build
import android.os.Handler
import android.os.Looper
import com.example.perjalanan.data.model.MpuSensorData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.ArrayDeque
import java.util.UUID

enum class BleConnectionState {
    DISCONNECTED,
    SCANNING,
    CONNECTING,
    CONNECTED,
    FAILED
}

data class DiscoveredBleDevice(
    val name: String,
    val address: String,
    val rssi: Int = 0
)

class BleManager(private val context: Context) {

    companion object {
        const val TARGET_DEVICE_NAME = "ESP32-Pulse"

        // Standard Bluetooth SIG UUIDs
        val HEART_RATE_SERVICE_UUID: UUID = UUID.fromString("0000180D-0000-1000-8000-00805F9B34FB")
        val HEART_RATE_MEASUREMENT_UUID: UUID = UUID.fromString("00002A37-0000-1000-8000-00805F9B34FB")
        val BATTERY_SERVICE_UUID: UUID = UUID.fromString("0000180F-0000-1000-8000-00805F9B34FB")
        val BATTERY_LEVEL_UUID: UUID = UUID.fromString("00002A19-0000-1000-8000-00805F9B34FB")
        val CCCD_UUID: UUID = UUID.fromString("00002902-0000-1000-8000-00805F9B34FB")
    }

    private val bluetoothManager: BluetoothManager? =
        context.getSystemService(Context.BLUETOOTH_SERVICE) as? BluetoothManager
    private val bluetoothAdapter: BluetoothAdapter? = bluetoothManager?.adapter

    private val _connectionState = MutableStateFlow(BleConnectionState.DISCONNECTED)
    val connectionState: StateFlow<BleConnectionState> = _connectionState.asStateFlow()

    private val _connectedDeviceName = MutableStateFlow<String?>(null)
    val connectedDeviceName: StateFlow<String?> = _connectedDeviceName.asStateFlow()

    private val _discoveredDevices = MutableStateFlow<List<DiscoveredBleDevice>>(emptyList())
    val discoveredDevices: StateFlow<List<DiscoveredBleDevice>> = _discoveredDevices.asStateFlow()

    private val _heartRateBpm = MutableStateFlow(0)
    val heartRateBpm: StateFlow<Int> = _heartRateBpm.asStateFlow()

    private val _mpuData = MutableStateFlow(MpuSensorData())
    val mpuData: StateFlow<MpuSensorData> = _mpuData.asStateFlow()

    private val _batteryPercent = MutableStateFlow(100)
    val batteryPercent: StateFlow<Int> = _batteryPercent.asStateFlow()

    private val _isUsbPowered = MutableStateFlow(true)
    val isUsbPowered: StateFlow<Boolean> = _isUsbPowered.asStateFlow()

    private val _latestTelemetry = MutableStateFlow("")
    val latestTelemetry: StateFlow<String> = _latestTelemetry.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val prefs = context.getSharedPreferences("perjalanan_ble_prefs", Context.MODE_PRIVATE)

    private val _lastSavedDevice = MutableStateFlow<DiscoveredBleDevice?>(
        prefs.getString("last_addr", "D0:EF:76:5D:B2:0A")?.let { addr ->
            DiscoveredBleDevice(
                name = prefs.getString("last_name", TARGET_DEVICE_NAME) ?: TARGET_DEVICE_NAME,
                address = addr
            )
        }
    )
    val lastSavedDevice: StateFlow<DiscoveredBleDevice?> = _lastSavedDevice.asStateFlow()

    fun saveLastDevice(name: String, address: String) {
        prefs.edit()
            .putString("last_addr", address)
            .putString("last_name", name)
            .apply()
        _lastSavedDevice.value = DiscoveredBleDevice(name, address)
    }

    fun isLocationEnabled(): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as? LocationManager
            ?: return true
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            locationManager.isLocationEnabled
        } else {
            locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
            locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        }
    }

    private var bluetoothGatt: BluetoothGatt? = null
    private val mainHandler = Handler(Looper.getMainLooper())
    private var isScanning = false

    // Descriptor queue for reliable asynchronous notifications enablement
    private val descriptorQueue = ArrayDeque<BluetoothGattDescriptor>()
    private var isWritingDescriptor = false

    private val scanCallback = object : ScanCallback() {
        @SuppressLint("MissingPermission")
        override fun onScanResult(callbackType: Int, result: ScanResult?) {
            result ?: return
            val device = result.device ?: return
            val name = device.name ?: result.scanRecord?.deviceName ?: "Perangkat BLE"
            val address = device.address

            val currentList = _discoveredDevices.value.toMutableList()
            val existingIndex = currentList.indexOfFirst { it.address == address }
            val discovered = DiscoveredBleDevice(name = name, address = address, rssi = result.rssi)

            if (existingIndex >= 0) {
                currentList[existingIndex] = discovered
            } else {
                currentList.add(discovered)
            }
            _discoveredDevices.value = currentList
        }

        override fun onScanFailed(errorCode: Int) {
            if (errorCode == ScanCallback.SCAN_FAILED_ALREADY_STARTED) {
                // Scan sudah berjalan di background, bukan error fatal
                isScanning = true
                _connectionState.value = BleConnectionState.SCANNING
                return
            }
            isScanning = false
            _connectionState.value = BleConnectionState.FAILED
            _errorMessage.value = "Pencarian BLE gagal (kode error: $errorCode)"
        }
    }

    private val gattCallback = object : BluetoothGattCallback() {
        @SuppressLint("MissingPermission")
        override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int) {
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                _connectionState.value = BleConnectionState.CONNECTING
                val deviceName = gatt?.device?.name ?: TARGET_DEVICE_NAME
                _connectedDeviceName.value = deviceName
                gatt?.device?.address?.let { addr ->
                    saveLastDevice(deviceName, addr)
                }
                mainHandler.postDelayed({
                    gatt?.discoverServices()
                }, 400)
            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                // Callback pemutusan koneksi diterima dari BLE controller
                mainHandler.post {
                    try {
                        gatt?.close()
                    } catch (_: Exception) {}
                    if (bluetoothGatt == gatt) {
                        bluetoothGatt = null
                    }
                    _connectionState.value = BleConnectionState.DISCONNECTED
                    _connectedDeviceName.value = null
                    _heartRateBpm.value = 0
                }
            } else if (status != BluetoothGatt.GATT_SUCCESS) {
                cleanUpConnection()
                _connectionState.value = BleConnectionState.FAILED
                _errorMessage.value = "Koneksi terputus (status: $status)"
            }
        }

        @SuppressLint("MissingPermission")
        override fun onServicesDiscovered(gatt: BluetoothGatt?, status: Int) {
            if (status == BluetoothGatt.GATT_SUCCESS && gatt != null) {
                _connectionState.value = BleConnectionState.CONNECTED
                subscribeAllCharacteristics(gatt)
            } else {
                _connectionState.value = BleConnectionState.FAILED
                _errorMessage.value = "Gagal membaca service GATT alat"
            }
        }

        // Android 13+ (API 33+)
        override fun onCharacteristicChanged(
            gatt: BluetoothGatt,
            characteristic: BluetoothGattCharacteristic,
            value: ByteArray
        ) {
            handleCharacteristicData(characteristic.uuid, value)
        }

        // Deprecated callback for Android < 13
        @Deprecated("Used for Android 12 and below")
        override fun onCharacteristicChanged(
            gatt: BluetoothGatt?,
            characteristic: BluetoothGattCharacteristic?
        ) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU && characteristic != null) {
                @Suppress("DEPRECATION")
                handleCharacteristicData(characteristic.uuid, characteristic.value ?: byteArrayOf())
            }
        }

        @SuppressLint("MissingPermission")
        override fun onDescriptorWrite(
            gatt: BluetoothGatt?,
            descriptor: BluetoothGattDescriptor?,
            status: Int
        ) {
            isWritingDescriptor = false
            processNextDescriptorQueue()
        }

        override fun onCharacteristicRead(
            gatt: BluetoothGatt,
            characteristic: BluetoothGattCharacteristic,
            value: ByteArray,
            status: Int
        ) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                handleCharacteristicData(characteristic.uuid, value)
            }
        }
    }

    private fun handleCharacteristicData(uuid: UUID, data: ByteArray) {
        if (data.isEmpty()) return

        when (uuid) {
            HEART_RATE_MEASUREMENT_UUID -> {
                // Heart Rate 0x2A37 parsing:
                // Byte 0: Flags (Bit 0: 0 = uint8 BPM, 1 = uint16 BPM)
                val flag = data[0].toInt()
                val format16Bit = (flag and 0x01) != 0
                val bpm = if (format16Bit && data.size >= 3) {
                    (data[1].toInt() and 0xFF) or ((data[2].toInt() and 0xFF) shl 8)
                } else if (data.size >= 2) {
                    data[1].toInt() and 0xFF
                } else 0

                _heartRateBpm.value = bpm
            }

            BATTERY_LEVEL_UUID -> {
                // Battery Level 0x2A19 parsing (0-100%)
                val level = data[0].toInt() and 0xFF
                _batteryPercent.value = level
                _isUsbPowered.value = false
            }

            else -> {
                // Text from MPU6050 or UART NUS
                val text = String(data, Charsets.UTF_8).trim()
                if (text.isNotEmpty()) {
                    _latestTelemetry.value = text
                    parseTextTelemetry(text)
                }
            }
        }
    }

    private fun parseTextTelemetry(text: String) {
        // Parse MPU: "AX:-0.83 AY:0.40 AZ:0.29 | GX:0.1 GY:-0.5 GZ:0.9 | DIAM" or "BERGERAK"
        if (text.contains("AX:") || text.contains("GX:") || text.contains("DIAM") || text.contains("BERGERAK")) {
            val isMoving = text.contains("BERGERAK", ignoreCase = true)
            var ax = _mpuData.value.ax
            var ay = _mpuData.value.ay
            var az = _mpuData.value.az
            var gx = _mpuData.value.gx
            var gy = _mpuData.value.gy
            var gz = _mpuData.value.gz

            try {
                Regex("AX:([\\-\\d\\.]+)").find(text)?.groupValues?.get(1)?.toFloatOrNull()?.let { ax = it }
                Regex("AY:([\\-\\d\\.]+)").find(text)?.groupValues?.get(1)?.toFloatOrNull()?.let { ay = it }
                Regex("AZ:([\\-\\d\\.]+)").find(text)?.groupValues?.get(1)?.toFloatOrNull()?.let { az = it }
                Regex("GX:([\\-\\d\\.]+)").find(text)?.groupValues?.get(1)?.toFloatOrNull()?.let { gx = it }
                Regex("GY:([\\-\\d\\.]+)").find(text)?.groupValues?.get(1)?.toFloatOrNull()?.let { gy = it }
                Regex("GZ:([\\-\\d\\.]+)").find(text)?.groupValues?.get(1)?.toFloatOrNull()?.let { gz = it }
            } catch (_: Exception) {}

            _mpuData.value = MpuSensorData(
                ax = ax, ay = ay, az = az,
                gx = gx, gy = gy, gz = gz,
                isMoving = isMoving,
                motionLabel = if (isMoving) "Bergerak" else "Diam",
                rawString = text
            )
        }

        // Also parse UART TX: "BPM:72 | GERAK:0 | ADC:2100 | ACC:0.02g | GYRO:3dps"
        if (text.contains("BPM:")) {
            try {
                Regex("BPM:(\\d+)").find(text)?.groupValues?.get(1)?.toIntOrNull()?.let { bpmVal ->
                    _heartRateBpm.value = bpmVal
                }
            } catch (_: Exception) {}
        }
    }

    @SuppressLint("MissingPermission")
    private fun subscribeAllCharacteristics(gatt: BluetoothGatt) {
        descriptorQueue.clear()
        isWritingDescriptor = false

        // 1. Heart Rate Measurement
        val hrService = gatt.getService(HEART_RATE_SERVICE_UUID)
        hrService?.getCharacteristic(HEART_RATE_MEASUREMENT_UUID)?.let { ch ->
            gatt.setCharacteristicNotification(ch, true)
            ch.getDescriptor(CCCD_UUID)?.let { descriptorQueue.add(it) }
        }

        // 2. Battery Service (if present)
        val batService = gatt.getService(BATTERY_SERVICE_UUID)
        batService?.getCharacteristic(BATTERY_LEVEL_UUID)?.let { ch ->
            gatt.setCharacteristicNotification(ch, true)
            ch.getDescriptor(CCCD_UUID)?.let { descriptorQueue.add(it) }
            // Also trigger a one-shot read
            mainHandler.postDelayed({ gatt.readCharacteristic(ch) }, 200)
        }

        // 3. MPU6050 & UART NUS Custom characteristics
        for (service in gatt.services) {
            val uuidStr = service.uuid.toString().uppercase()
            if (uuidStr.startsWith("5AFE0001") || uuidStr.startsWith("5AFE0011")) {
                for (ch in service.characteristics) {
                    val chUuid = ch.uuid.toString().uppercase()
                    // 5AFE0003 is NUS TX (notify), 5AFE0012 is MPU DATA (notify)
                    if (chUuid.startsWith("5AFE0003") || chUuid.startsWith("5AFE0012")) {
                        gatt.setCharacteristicNotification(ch, true)
                        ch.getDescriptor(CCCD_UUID)?.let { descriptorQueue.add(it) }
                    }
                }
            }
        }

        processNextDescriptorQueue()
    }

    @SuppressLint("MissingPermission")
    private fun processNextDescriptorQueue() {
        if (isWritingDescriptor) return
        val desc = descriptorQueue.poll() ?: return
        isWritingDescriptor = true

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            bluetoothGatt?.writeDescriptor(desc, BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE)
        } else {
            @Suppress("DEPRECATION")
            desc.value = BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE
            @Suppress("DEPRECATION")
            bluetoothGatt?.writeDescriptor(desc)
        }
    }

    @SuppressLint("MissingPermission")
    fun startScan() {
        // Hentikan scan sebelumnya jika masih aktif untuk mencegah SCAN_FAILED_ALREADY_STARTED (kode 1)
        stopScan()

        if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled) {
            _connectionState.value = BleConnectionState.FAILED
            _errorMessage.value = "Bluetooth tidak aktif. Silakan aktifkan Bluetooth di HP Anda."
            return
        }

        // Pada Android 11 ke bawah (termasuk Android 8.1 Oreo), Layanan Lokasi (GPS) wajib aktif agar scan BLE membuahkan hasil
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S && !isLocationEnabled()) {
            _errorMessage.value = "Layanan Lokasi (GPS) belum aktif. Mohon nyalakan GPS di pengaturan HP agar perangkat Bluetooth dapat terdeteksi."
        } else {
            _errorMessage.value = null
        }

        val scanner = bluetoothAdapter.bluetoothLeScanner
        if (scanner == null) {
            _errorMessage.value = "Scanner BLE tidak tersedia di perangkat ini."
            return
        }

        _discoveredDevices.value = emptyList()
        _connectionState.value = BleConnectionState.SCANNING
        isScanning = true

        val scanSettings = ScanSettings.Builder()
            .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
            .setReportDelay(0)
            .build()

        try {
            scanner.startScan(null, scanSettings, scanCallback)
        } catch (e: Exception) {
            try {
                scanner.startScan(scanCallback)
            } catch (e2: Exception) {
                isScanning = false
                _connectionState.value = BleConnectionState.FAILED
                _errorMessage.value = "Gagal memulai scan: ${e2.message}"
                return
            }
        }

        // Auto stop scan after 12 seconds
        mainHandler.postDelayed({
            if (isScanning) {
                stopScan()
            }
        }, 12000)
    }

    @SuppressLint("MissingPermission")
    fun stopScan() {
        if (!isScanning) return
        isScanning = false
        val scanner = bluetoothAdapter?.bluetoothLeScanner
        try {
            scanner?.stopScan(scanCallback)
        } catch (_: Exception) {}

        if (_connectionState.value == BleConnectionState.SCANNING) {
            _connectionState.value = BleConnectionState.DISCONNECTED
        }
    }

    @SuppressLint("MissingPermission")
    fun connect(deviceAddress: String) {
        stopScan()

        if (bluetoothAdapter == null) {
            _errorMessage.value = "Bluetooth tidak didukung"
            return
        }

        val device = try {
            bluetoothAdapter.getRemoteDevice(deviceAddress)
        } catch (e: Exception) {
            _errorMessage.value = "Alamat perangkat tidak valid: ${e.message}"
            return
        }

        cleanUpConnection()
        val dName = try { device.name ?: TARGET_DEVICE_NAME } catch (_: Exception) { TARGET_DEVICE_NAME }
        saveLastDevice(dName, deviceAddress)
        _connectionState.value = BleConnectionState.CONNECTING

        bluetoothGatt = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            device.connectGatt(context, false, gattCallback, BluetoothDevice.TRANSPORT_LE)
        } else {
            device.connectGatt(context, false, gattCallback)
        }
    }

    @SuppressLint("MissingPermission")
    fun disconnect() {
        stopScan()
        val gatt = bluetoothGatt
        _connectionState.value = BleConnectionState.DISCONNECTED
        _connectedDeviceName.value = null
        _heartRateBpm.value = 0
        descriptorQueue.clear()
        isWritingDescriptor = false

        if (gatt != null) {
            try {
                // Kirim perintah disconnect ke ESP32 agar ESP32 menerima event LL_TERMINATE_IND
                // dan callback onDisconnect() di ESP32 langsung memanggil BLEDevice::startAdvertising()
                gatt.disconnect()
            } catch (_: Exception) {}

            // Delay penutupan socket gatt (500ms) agar paket disconnect terkirim sempurna sebelum socket dibunuh
            mainHandler.postDelayed({
                try {
                    gatt.close()
                } catch (_: Exception) {}
                if (bluetoothGatt == gatt) {
                    bluetoothGatt = null
                }
            }, 500)
        }
    }

    @SuppressLint("MissingPermission")
    private fun cleanUpConnection() {
        val gatt = bluetoothGatt
        bluetoothGatt = null
        descriptorQueue.clear()
        isWritingDescriptor = false
        try {
            gatt?.disconnect()
            gatt?.close()
        } catch (_: Exception) {}
    }
}
