package com.example.perjalanan.ui.components

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bluetooth
import androidx.compose.material.icons.filled.BluetoothConnected
import androidx.compose.material.icons.filled.BluetoothSearching
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.perjalanan.data.ble.BleConnectionState
import com.example.perjalanan.data.ble.BleManager
import com.example.perjalanan.data.ble.DiscoveredBleDevice
import com.example.perjalanan.ui.localization.LocalAppStrings
import com.example.perjalanan.ui.theme.LocalAppColors
import com.example.perjalanan.ui.theme.CardBg
import com.example.perjalanan.ui.theme.GreenSafe
import com.example.perjalanan.ui.theme.Maroon
import com.example.perjalanan.ui.theme.Peach
import com.example.perjalanan.ui.theme.SoftGreen
import com.example.perjalanan.ui.theme.SoftPeach
import com.example.perjalanan.ui.theme.SoftRed
import com.example.perjalanan.ui.theme.SosRed
import com.example.perjalanan.ui.theme.StrokeColor
import com.example.perjalanan.ui.theme.TextDark
import com.example.perjalanan.ui.theme.TextGray
import com.example.perjalanan.ui.theme.White

@Composable
fun BleDeviceDialog(
    connectionState: BleConnectionState,
    connectedDeviceName: String?,
    discoveredDevices: List<DiscoveredBleDevice>,
    lastSavedDevice: DiscoveredBleDevice? = null,
    errorMessage: String?,
    onStartScan: () -> Unit,
    onStopScan: () -> Unit,
    onConnect: (String) -> Unit,
    onDisconnect: () -> Unit,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val strings = LocalAppStrings.current
    val colors = LocalAppColors.current

    // Bluetooth runtime permission launcher
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val allGranted = permissions.values.all { it }
        if (allGranted) {
            onStartScan()
        }
    }

    LaunchedEffect(Unit) {
        val hasPermissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_SCAN) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED
        } else {
            ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        }

        if (hasPermissions) {
            onStartScan()
        } else {
            val permissionsToRequest = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                arrayOf(
                    Manifest.permission.BLUETOOTH_SCAN,
                    Manifest.permission.BLUETOOTH_CONNECT
                )
            } else {
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            }
            permissionLauncher.launch(permissionsToRequest)
        }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        shape = RoundedCornerShape(20.dp),
        containerColor = CardBg,
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(if (connectionState == BleConnectionState.CONNECTED) SoftGreen else SoftPeach),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = if (connectionState == BleConnectionState.CONNECTED) Icons.Default.BluetoothConnected else Icons.Default.Bluetooth,
                            contentDescription = null,
                            tint = if (connectionState == BleConnectionState.CONNECTED) GreenSafe else Maroon,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = strings.connectDeviceTitle,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Maroon
                    )
                }

                if (connectionState == BleConnectionState.SCANNING) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = Maroon,
                        strokeWidth = 2.dp
                    )
                } else if (connectionState != BleConnectionState.CONNECTED) {
                    IconButton(onClick = onStartScan) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = strings.rescan,
                            tint = Maroon
                        )
                    }
                }
            }
        },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                // Status Section
                when (connectionState) {
                    BleConnectionState.CONNECTED -> {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(SoftGreen)
                                .padding(12.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = null,
                                    tint = GreenSafe,
                                    modifier = Modifier.size(22.dp)
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text(
                                        text = strings.connectedStatus,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp,
                                        color = GreenSafe
                                    )
                                    Text(
                                        text = connectedDeviceName ?: BleManager.TARGET_DEVICE_NAME,
                                        fontSize = 12.sp,
                                        color = TextDark
                                    )
                                }
                            }
                        }
                    }

                    BleConnectionState.CONNECTING -> {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(SoftPeach)
                                .padding(12.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(18.dp),
                                    color = Maroon,
                                    strokeWidth = 2.dp
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = "${strings.connectingTo} ${connectedDeviceName ?: "device"}...",
                                    fontSize = 13.sp,
                                    color = TextDark,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }

                    BleConnectionState.SCANNING -> {
                        Text(
                            text = strings.scanningNearby,
                            fontSize = 12.sp,
                            color = TextGray,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }

                    BleConnectionState.FAILED -> {
                        Text(
                            text = errorMessage ?: strings.connectionFailedDesc,
                            fontSize = 12.sp,
                            color = SosRed,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }

                    BleConnectionState.DISCONNECTED -> {
                        Text(
                            text = strings.selectBleDevice,
                            fontSize = 12.sp,
                            color = TextGray,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }
                }

                if (!errorMessage.isNullOrEmpty() && connectionState != BleConnectionState.FAILED) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(SoftRed)
                            .border(1.dp, SosRed.copy(alpha = 0.5f), RoundedCornerShape(10.dp))
                            .padding(10.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Warning,
                                contentDescription = null,
                                tint = SosRed,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = errorMessage,
                                fontSize = 11.sp,
                                color = SosRed,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }

                // Quick Reconnect to Last Saved Device
                if (lastSavedDevice != null && connectionState != BleConnectionState.CONNECTED && connectionState != BleConnectionState.CONNECTING) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 10.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = SoftPeach),
                        border = BorderStroke(1.dp, Maroon)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp, vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.History,
                                    contentDescription = null,
                                    tint = Maroon,
                                    modifier = Modifier.size(24.dp)
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text(
                                        text = "Perangkat Terakhir Terhubung",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Maroon
                                    )
                                    Text(
                                        text = lastSavedDevice.name,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = TextDark
                                    )
                                    Text(
                                        text = lastSavedDevice.address,
                                        fontSize = 10.sp,
                                        color = TextGray
                                    )
                                }
                            }
                            Button(
                                onClick = { onConnect(lastSavedDevice.address) },
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Maroon),
                                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                                modifier = Modifier.height(34.dp)
                            ) {
                                Text("Sambungkan", fontSize = 11.sp, color = White)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                // Devices list (only shown if not connected)
                if (connectionState != BleConnectionState.CONNECTED) {
                    if (discoveredDevices.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp)
                                .border(1.dp, StrokeColor, RoundedCornerShape(12.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(
                                    imageVector = Icons.Default.BluetoothSearching,
                                    contentDescription = null,
                                    tint = TextGray,
                                    modifier = Modifier.size(32.dp)
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = if (connectionState == BleConnectionState.SCANNING)
                                        "Menunggu respons '${BleManager.TARGET_DEVICE_NAME}'..."
                                    else
                                        "Tidak ada perangkat ditemukan",
                                    fontSize = 12.sp,
                                    color = TextGray
                                )
                            }
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(180.dp)
                        ) {
                            items(discoveredDevices) { device ->
                                val isTarget = device.name.contains(BleManager.TARGET_DEVICE_NAME, ignoreCase = true)
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp)
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(if (isTarget) SoftPeach else CardBg)
                                        .border(1.dp, if (isTarget) Maroon else StrokeColor, RoundedCornerShape(12.dp))
                                        .clickable { onConnect(device.address) }
                                        .padding(horizontal = 12.dp, vertical = 10.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Text(
                                                text = device.name,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 13.sp,
                                                color = if (isTarget) Maroon else TextDark,
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis
                                            )
                                            if (isTarget) {
                                                Spacer(modifier = Modifier.width(6.dp))
                                                Box(
                                                    modifier = Modifier
                                                        .clip(RoundedCornerShape(6.dp))
                                                        .background(Maroon)
                                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                                ) {
                                                    Text(
                                                        text = "Smartwatch",
                                                        fontSize = 9.sp,
                                                        fontWeight = FontWeight.Bold,
                                                        color = White
                                                    )
                                                }
                                            }
                                        }
                                        Text(
                                            text = device.address,
                                            fontSize = 11.sp,
                                            color = TextGray
                                        )
                                    }

                                    Button(
                                        onClick = { onConnect(device.address) },
                                        shape = RoundedCornerShape(8.dp),
                                        colors = ButtonDefaults.buttonColors(containerColor = Maroon),
                                        modifier = Modifier.height(34.dp)
                                    ) {
                                        Text(strings.btnConnect, fontSize = 11.sp, color = White)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            if (connectionState == BleConnectionState.CONNECTED) {
                OutlinedButton(
                    onClick = onDisconnect,
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = SosRed)
                ) {
                    Text(strings.btnDisconnect, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                }
            } else if (connectionState != BleConnectionState.CONNECTING) {
                Button(
                    onClick = onStartScan,
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Maroon)
                ) {
                    Text(strings.rescan, fontSize = 13.sp, color = White)
                }
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(strings.close, color = Maroon)
            }
        }
    )
}
