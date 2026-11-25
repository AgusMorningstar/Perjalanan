package com.example.perjalanan.ui.screens.home

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BatteryStd
import androidx.compose.material.icons.filled.Bluetooth
import androidx.compose.material.icons.filled.BluetoothConnected
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Power
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.example.perjalanan.data.ble.BleConnectionState
import com.example.perjalanan.data.notification.SosNotificationHelper
import com.example.perjalanan.ui.components.BleDeviceDialog
import com.example.perjalanan.ui.components.PerjalananBottomBar
import com.example.perjalanan.ui.components.SosAlertData
import com.example.perjalanan.ui.components.SosEmergencyDialog
import com.example.perjalanan.ui.localization.LocalAppStrings
import com.example.perjalanan.ui.theme.LocalAppColors
import com.example.perjalanan.ui.navigation.Screen
import com.example.perjalanan.ui.theme.AlertEnd
import com.example.perjalanan.ui.theme.AlertStart
import com.example.perjalanan.ui.theme.CardBg
import com.example.perjalanan.ui.theme.Cream
import com.example.perjalanan.ui.theme.GreenSafe
import com.example.perjalanan.ui.theme.HeroEnd
import com.example.perjalanan.ui.theme.HeroStart
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
fun HomeScreen(
    onNavigate: (Screen) -> Unit,
    viewModel: HomeViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val strings = LocalAppStrings.current
    val colors = LocalAppColors.current
    var activeIncomingSos by remember { mutableStateOf<SosAlertData?>(null) }
    var isNotificationDialogOpen by remember { mutableStateOf(false) }

    LaunchedEffect(state.sosSentMessage) {
        state.sosSentMessage?.let { msg ->
            snackbarHostState.showSnackbar(msg)
            viewModel.clearSosMessage()
        }
    }

    // POP-UP DIALOG SINYAL DARURAT MASUK DARI KORBAN TERDEKAT (dialog_sos.xml)
    if (activeIncomingSos != null) {
        SosEmergencyDialog(
            data = activeIncomingSos!!,
            onAcceptAndNavigate = {
                activeIncomingSos = null
                onNavigate(Screen.Map)
            },
            onMuteAlarm = {
                activeIncomingSos = null
            },
            onDismiss = {
                activeIncomingSos = null
            }
        )
    }

    if (state.isBleDialogOpen) {
        BleDeviceDialog(
            connectionState = state.bleState,
            connectedDeviceName = state.connectedDeviceName,
            discoveredDevices = state.discoveredDevices,
            lastSavedDevice = state.lastSavedDevice,
            errorMessage = state.bleErrorMessage,
            onStartScan = viewModel::startBleScan,
            onStopScan = viewModel::stopBleScan,
            onConnect = viewModel::connectDevice,
            onDisconnect = viewModel::disconnectDevice,
            onDismiss = viewModel::closeBleDialog
        )
    }

    if (isNotificationDialogOpen) {
        AlertDialog(
            onDismissRequest = { isNotificationDialogOpen = false },
            shape = RoundedCornerShape(20.dp),
            containerColor = CardBg,
            icon = {
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .clip(CircleShape)
                        .background(Peach),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = null,
                        tint = Maroon,
                        modifier = Modifier.size(28.dp)
                    )
                }
            },
            title = {
                Text(
                    text = strings.notificationTitle,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = TextDark
                )
            },
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Card(
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = SoftGreen),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Shield,
                                contentDescription = null,
                                tint = GreenSafe,
                                modifier = Modifier.size(22.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = strings.notificationSystemActive,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextDark
                                )
                                Text(
                                    text = strings.notificationSystemActiveDesc,
                                    fontSize = 11.sp,
                                    color = TextGray
                                )
                            }
                        }
                    }

                    Text(
                        text = strings.notificationEmpty,
                        fontSize = 12.sp,
                        color = TextGray,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp)
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = { isNotificationDialogOpen = false },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Maroon)
                ) {
                    Text(strings.close, color = White, fontWeight = FontWeight.Bold)
                }
            }
        )
    }

    if (state.isSosDialogOpen) {
        AlertDialog(
            onDismissRequest = viewModel::closeSosDialog,
            shape = RoundedCornerShape(20.dp),
            containerColor = CardBg,
            icon = {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = null,
                    tint = SosRed,
                    modifier = Modifier.size(36.dp)
                )
            },
            title = {
                Text(
                    text = strings.sosSendConfirmTitle,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = TextDark
                )
            },
            text = {
                Text(
                    text = strings.sosSendConfirmBody,
                    fontSize = 14.sp,
                    color = TextGray
                )
            },
            confirmButton = {
                Button(
                    onClick = viewModel::sendSos,
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = SosRed)
                ) {
                    Text(strings.btnSendNow, color = White, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = viewModel::closeSosDialog,
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(strings.btnCancel, color = Maroon)
                }
            }
        )
    }

    Scaffold(
        bottomBar = {
            PerjalananBottomBar(
                currentRoute = Screen.Home.route,
                onNavigate = onNavigate
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = Cream
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            // HERO DASHBOARD SECTION
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(listOf(HeroStart, HeroEnd))
                    )
                    .padding(20.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(46.dp)
                                    .clip(CircleShape)
                                    .background(Peach),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = null,
                                    tint = Maroon,
                                    modifier = Modifier.size(28.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = "${strings.helloGreeting}, ${state.user.name.ifBlank { strings.defaultUserName }}!",
                                    color = White,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = strings.monitoringActive,
                                    color = Peach,
                                    fontSize = 12.sp
                                )
                            }
                        }

                        IconButton(
                            onClick = { isNotificationDialogOpen = true },
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(Color(0x22FFFFFF))
                        ) {
                            Icon(
                                imageVector = Icons.Default.Notifications,
                                contentDescription = strings.notificationTitle,
                                tint = White
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    // Status Keamanan Card
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0x33FFFFFF)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Shield,
                                    contentDescription = null,
                                    tint = GreenSafe,
                                    modifier = Modifier.size(24.dp)
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text(
                                        text = strings.securityStatusLabel,
                                        color = White,
                                        fontSize = 11.sp
                                    )
                                    Text(
                                        text = if (state.healthMetrics.securityStatus.equals("Aman", ignoreCase = true)) strings.securitySafe else state.healthMetrics.securityStatus,
                                        color = White,
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }

                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(SoftGreen)
                                    .padding(horizontal = 10.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = strings.statusProtected,
                                    color = GreenSafe,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }

            // SENSORS & METRICS
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = strings.smartwatchMonitoring,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDark,
                    modifier = Modifier.padding(bottom = 10.dp)
                )

                // BLE Smartwatch Status Banner
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (state.bleState == BleConnectionState.CONNECTED) SoftGreen else SoftPeach
                    ),
                    border = CardDefaults.outlinedCardBorder().copy(
                        brush = androidx.compose.ui.graphics.SolidColor(
                            if (state.bleState == BleConnectionState.CONNECTED) GreenSafe else Maroon
                        )
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 14.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(
                                imageVector = if (state.bleState == BleConnectionState.CONNECTED) Icons.Default.BluetoothConnected else Icons.Default.Bluetooth,
                                contentDescription = null,
                                tint = if (state.bleState == BleConnectionState.CONNECTED) GreenSafe else Maroon,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = if (state.bleState == BleConnectionState.CONNECTED)
                                        strings.espConnected
                                    else
                                        strings.espDisconnected,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (state.bleState == BleConnectionState.CONNECTED) GreenSafe else TextDark
                                )
                                Text(
                                    text = if (state.bleState == BleConnectionState.CONNECTED)
                                        strings.espReceivingLive
                                    else
                                        strings.espTapToConnect,
                                    fontSize = 11.sp,
                                    color = TextGray
                                )
                            }
                        }

                        Button(
                            onClick = viewModel::openBleDialog,
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (state.bleState == BleConnectionState.CONNECTED) GreenSafe else Maroon
                            ),
                            modifier = Modifier.height(34.dp)
                        ) {
                            Text(
                                text = if (state.bleState == BleConnectionState.CONNECTED) strings.btnManage else strings.btnConnect,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = White
                            )
                        }
                    }
                }

                // Row 1: Heart Rate + Motion
                Row(modifier = Modifier.fillMaxWidth()) {
                    MetricCard(
                        title = strings.heartRateTitle,
                        value = if (state.healthMetrics.heartRate > 0)
                            "${state.healthMetrics.heartRate}"
                        else if (state.bleState == BleConnectionState.CONNECTED)
                            "--"
                        else
                            "0",
                        unit = "bpm",
                        status = if (state.healthMetrics.heartRateState.equals("Menunggu Sensor", ignoreCase = true)) strings.heartRateWaiting else state.healthMetrics.heartRateState,
                        icon = Icons.Default.Favorite,
                        iconColor = SosRed,
                        iconBg = SoftRed,
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    MetricCard(
                        title = strings.motionSensorTitle,
                        value = if (state.bleState == BleConnectionState.CONNECTED)
                            state.healthMetrics.motionState
                        else
                            strings.motionStill,
                        unit = "",
                        status = if (state.bleState == BleConnectionState.CONNECTED)
                            "AX: ${String.format("%.2f", state.healthMetrics.mpuData.ax)}g | AY: ${String.format("%.2f", state.healthMetrics.mpuData.ay)}g"
                        else
                            strings.motionDeviceDisconnected,
                        icon = Icons.Default.DirectionsRun,
                        iconColor = Maroon,
                        iconBg = SoftPeach,
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Row 2: Battery + GPS
                Row(modifier = Modifier.fillMaxWidth()) {
                    MetricCard(
                        title = strings.powerStatusLabel,
                        value = if (state.bleState == BleConnectionState.CONNECTED)
                            (if (state.healthMetrics.isUsbPowered) "USB" else "${state.healthMetrics.batteryPercent}%")
                        else
                            "Offline",
                        unit = if (state.bleState == BleConnectionState.CONNECTED && state.healthMetrics.isUsbPowered) "⚡" else "",
                        status = if (state.bleState == BleConnectionState.CONNECTED)
                            (if (state.healthMetrics.isUsbPowered) strings.powerUsbActive else strings.powerBatteryNormal)
                        else
                            strings.powerDeviceDisconnected,
                        icon = if (state.healthMetrics.isUsbPowered) Icons.Default.Power else Icons.Default.BatteryStd,
                        iconColor = GreenSafe,
                        iconBg = SoftGreen,
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    MetricCard(
                        title = strings.gpsLocationLabel,
                        value = strings.gpsActiveText,
                        unit = "",
                        status = "Makassar, ID",
                        icon = Icons.Default.LocationOn,
                        iconColor = Maroon,
                        iconBg = SoftPeach,
                        modifier = Modifier.weight(1f)
                    )
                }

                // Live MPU6050 6-Axis Telemetry Card (Visible when connected)
                if (state.bleState == BleConnectionState.CONNECTED) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = CardBg),
                        border = CardDefaults.outlinedCardBorder().copy(
                            brush = androidx.compose.ui.graphics.SolidColor(StrokeColor)
                        )
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.ShowChart,
                                        contentDescription = null,
                                        tint = Maroon,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = strings.mpuTelemetryTitle,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = TextDark
                                    )
                                }
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(if (state.healthMetrics.mpuData.isMoving) SoftRed else SoftGreen)
                                        .padding(horizontal = 8.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = state.healthMetrics.mpuData.motionLabel.uppercase(),
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (state.healthMetrics.mpuData.isMoving) SosRed else GreenSafe
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = strings.accelerationLabel,
                                        fontSize = 10.sp,
                                        color = TextGray,
                                        fontWeight = FontWeight.Medium
                                    )
                                    Text(
                                        text = "X: ${String.format("%.2f", state.healthMetrics.mpuData.ax)}  Y: ${String.format("%.2f", state.healthMetrics.mpuData.ay)}  Z: ${String.format("%.2f", state.healthMetrics.mpuData.az)}",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = TextDark
                                    )
                                }
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = strings.gyroscopeLabel,
                                        fontSize = 10.sp,
                                        color = TextGray,
                                        fontWeight = FontWeight.Medium
                                    )
                                    Text(
                                        text = "X: ${String.format("%.1f", state.healthMetrics.mpuData.gx)}  Y: ${String.format("%.1f", state.healthMetrics.mpuData.gy)}  Z: ${String.format("%.1f", state.healthMetrics.mpuData.gz)}",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = TextDark
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(28.dp))

                // GIANT SOS BUTTON
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
                    val pulseScale by infiniteTransition.animateFloat(
                        initialValue = 1f,
                        targetValue = 1.08f,
                        animationSpec = infiniteRepeatable(
                            animation = tween(1000, easing = FastOutSlowInEasing),
                            repeatMode = RepeatMode.Reverse
                        ),
                        label = "pulseScale"
                    )

                    Box(
                        modifier = Modifier
                            .size(140.dp)
                            .scale(pulseScale)
                            .clip(CircleShape)
                            .background(
                                brush = Brush.radialGradient(
                                    colors = listOf(AlertEnd, AlertStart)
                                )
                            )
                            .border(6.dp, Color(0x33B3141F), CircleShape)
                            .clickable { viewModel.openSosDialog() },
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "SOS",
                                color = White,
                                fontSize = 32.sp,
                                fontWeight = FontWeight.ExtraBold,
                                letterSpacing = 2.sp
                            )
                            Text(
                                text = strings.sosEmergencyLabel,
                                color = Peach,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = strings.sosHint,
                        color = TextGray,
                        fontSize = 12.sp
                    )
                }

                Spacer(modifier = Modifier.height(28.dp))

                // QUICK ACTIONS
                Text(
                    text = strings.quickActions,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDark,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    QuickActionItem(
                        icon = Icons.Default.Phone,
                        label = strings.actionCall110,
                        onClick = { onNavigate(Screen.Emergency) }
                    )
                    QuickActionItem(
                        icon = Icons.Default.LocationOn,
                        label = strings.actionVictimMap,
                        onClick = { onNavigate(Screen.Map) }
                    )
                    QuickActionItem(
                        icon = Icons.Default.Share,
                        label = strings.actionShare,
                        onClick = { /* Share link */ }
                    )
                    QuickActionItem(
                        icon = Icons.Default.Shield,
                        label = strings.actionPolice,
                        onClick = { onNavigate(Screen.Emergency) }
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                // SIMULASI NOTIFIKASI SOS WARGA (RADIUS 100-500m)
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = SoftPeach),
                    border = CardDefaults.outlinedCardBorder().copy(
                        brush = androidx.compose.ui.graphics.SolidColor(StrokeColor)
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 14.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = strings.citizenSimTitle,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Maroon
                            )
                            Text(
                                text = strings.citizenSimDesc,
                                fontSize = 10.sp,
                                color = TextGray
                            )
                        }

                        Button(
                            onClick = {
                                val citizenAlert = SosAlertData(
                                    id = "SOS-WARGA",
                                    victimName = "Ahmad Pratama",
                                    locationAddress = "Jl. Perintis Kemerdekaan KM 10",
                                    distanceText = "250 m",
                                    etaText = "3 ${strings.minutesAbbr}",
                                    heartRate = 132
                                )
                                // 1. Munculkan pop-up dialog in-app
                                activeIncomingSos = citizenAlert
                                // 2. Picu Heads-up Notification sistem Android
                                SosNotificationHelper.showEmergencyHeadsUpNotification(
                                    context = context,
                                    data = citizenAlert,
                                    isPoliceRole = false
                                )
                            },
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = SosRed),
                            modifier = Modifier.height(34.dp)
                        ) {
                            Text(strings.btnTestPopup, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = White)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Composable
fun MetricCard(
    title: String,
    value: String,
    unit: String,
    status: String,
    icon: ImageVector,
    iconColor: Color,
    iconBg: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardBg),
        border = CardDefaults.outlinedCardBorder().copy(
            brush = androidx.compose.ui.graphics.SolidColor(StrokeColor)
        )
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(iconBg),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = iconColor,
                        modifier = Modifier.size(18.dp)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = title,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextDark
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = value,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDark
                )
                if (unit.isNotEmpty()) {
                    Text(
                        text = " $unit",
                        fontSize = 12.sp,
                        color = TextGray,
                        modifier = Modifier.padding(bottom = 3.dp)
                    )
                }
            }

            Text(
                text = status,
                fontSize = 11.sp,
                color = TextGray,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

@Composable
fun QuickActionItem(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .size(54.dp)
                .clip(CircleShape)
                .background(CardBg)
                .border(1.dp, StrokeColor, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = Maroon,
                modifier = Modifier.size(24.dp)
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = label,
            fontSize = 11.sp,
            color = TextDark,
            fontWeight = FontWeight.Medium
        )
    }
}
