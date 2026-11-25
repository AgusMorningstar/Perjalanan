package com.example.perjalanan.ui.screens.police

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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocalPolice
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.NotificationsOff
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.platform.LocalContext
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.perjalanan.data.model.SosReport
import com.example.perjalanan.data.notification.SosNotificationHelper
import com.example.perjalanan.ui.components.PerjalananTopBar
import com.example.perjalanan.ui.components.PoliceBottomBar
import com.example.perjalanan.ui.components.SosAlertData
import com.example.perjalanan.ui.components.SosEmergencyDialog
import com.example.perjalanan.ui.localization.LocalAppStrings
import com.example.perjalanan.ui.theme.LocalAppColors
import com.example.perjalanan.ui.navigation.Screen
import com.example.perjalanan.ui.theme.CardBg
import com.example.perjalanan.ui.theme.Cream
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
fun PoliceDashboardScreen(
    onNavigateToPoliceProfile: () -> Unit,
    onNavigateBack: (() -> Unit)? = null,
    onNavigateToMap: () -> Unit = {},
    viewModel: PoliceDashboardViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val strings = LocalAppStrings.current
    val colors = LocalAppColors.current
    var activeSosAlert by remember { mutableStateOf<SosAlertData?>(null) }

    LaunchedEffect(state.actionMessage) {
        state.actionMessage?.let { msg ->
            snackbarHostState.showSnackbar(msg)
            viewModel.clearActionMessage()
        }
    }

    // POP-UP DIALOG SINYAL DARURAT (Sesuai dialog_sos.xml)
    if (activeSosAlert != null) {
        SosEmergencyDialog(
            data = activeSosAlert!!,
            onAcceptAndNavigate = {
                val alert = activeSosAlert!!
                viewModel.acceptReport(alert.id)
                activeSosAlert = null
                onNavigateToMap()
            },
            onMuteAlarm = {
                viewModel.stopAlarm()
                activeSosAlert = null
            },
            onDismiss = {
                activeSosAlert = null
            }
        )
    }

    Scaffold(
        topBar = {
            PerjalananTopBar(
                title = strings.policeDashboardTitle,
                onBackClick = onNavigateBack,
                actions = {
                    IconButton(onClick = onNavigateToPoliceProfile) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = strings.policeProfileTitle,
                            tint = White
                        )
                    }
                }
            )
        },
        bottomBar = {
            PoliceBottomBar(
                currentRoute = Screen.PoliceDashboard.route,
                onNavigate = { screen ->
                    when (screen) {
                        Screen.PoliceMap -> onNavigateToMap()
                        Screen.PoliceProfile -> onNavigateToPoliceProfile()
                        else -> {}
                    }
                }
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
                .padding(16.dp)
        ) {
            // OFFICER STATUS CARD
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = CardBg),
                border = CardDefaults.outlinedCardBorder().copy(
                    brush = androidx.compose.ui.graphics.SolidColor(StrokeColor)
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(Peach),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocalPolice,
                                contentDescription = null,
                                tint = Maroon,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = state.officer.name,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextDark
                            )
                            Text(
                                text = "${state.officer.id} • ${state.officer.unit}",
                                fontSize = 12.sp,
                                color = TextGray
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (state.officer.isOnline) SoftGreen else SoftRed)
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = if (state.officer.isOnline) strings.statusOnline else strings.statusOffline,
                            color = if (state.officer.isOnline) GreenSafe else SosRed,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // SUMMARY CARDS (Laporan Aktif, Selesai, Unit)
            Row(modifier = Modifier.fillMaxWidth()) {
                SummaryBox(
                    title = strings.summaryActive,
                    count = "${state.officer.activeReportsCount}",
                    color = SosRed,
                    bg = SoftRed,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(10.dp))
                SummaryBox(
                    title = strings.summaryResolved,
                    count = "${state.officer.resolvedReportsCount}",
                    color = GreenSafe,
                    bg = SoftGreen,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(10.dp))
                SummaryBox(
                    title = strings.summaryStandbyUnits,
                    count = "${state.officer.availableUnitsCount}",
                    color = Maroon,
                    bg = SoftPeach,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // ALARM DEVICE SETTINGS
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = CardBg),
                border = CardDefaults.outlinedCardBorder().copy(
                    brush = androidx.compose.ui.graphics.SolidColor(StrokeColor)
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = if (state.officer.isAlarmActive) Icons.Default.NotificationsActive else Icons.Default.NotificationsOff,
                                contentDescription = null,
                                tint = if (state.officer.isAlarmActive) SosRed else TextGray,
                                modifier = Modifier.size(22.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = strings.sosSirenAlarm,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextDark
                                )
                                Text(
                                    text = if (state.officer.isAlarmActive) strings.sirenActiveDesc else strings.sirenInactiveDesc,
                                    fontSize = 11.sp,
                                    color = TextGray
                                )
                            }
                        }

                        Switch(
                            checked = state.officer.isAlarmActive,
                            onCheckedChange = viewModel::toggleAlarm,
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = White,
                                checkedTrackColor = SosRed
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "${strings.sirenVolume}: ${state.officer.alarmVolume}%",
                        fontSize = 12.sp,
                        color = TextDark,
                        fontWeight = FontWeight.Medium
                    )

                    Slider(
                        value = state.officer.alarmVolume.toFloat(),
                        onValueChange = { viewModel.onVolumeChange(it.toInt()) },
                        valueRange = 0f..100f,
                        colors = SliderDefaults.colors(
                            thumbColor = Maroon,
                            activeTrackColor = Maroon,
                            inactiveTrackColor = StrokeColor
                        )
                    )

                    if (state.officer.isAlarmActive) {
                        Button(
                            onClick = viewModel::stopAlarm,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(44.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = SosRed)
                        ) {
                            Text(strings.btnStopAlarm, fontWeight = FontWeight.Bold, color = White)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // INCOMING SOS REPORTS
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = strings.incomingSosReports,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Maroon
                )

                OutlinedButton(
                    onClick = {
                        val alertData = SosAlertData(
                            id = "SOS-001",
                            victimName = "Salsabilaa",
                            locationAddress = "Jl. Pendidikan No.12, Makassar",
                            distanceText = "1.8 km",
                            etaText = "6 ${strings.minutesAbbr}",
                            heartRate = 128
                        )
                        // 1. Tampilkan Pop-up Dialog In-App (dialog_sos.xml)
                        activeSosAlert = alertData
                        // 2. Picu Heads-up Notification Sistem (pop-up banner layar atas)
                        SosNotificationHelper.showEmergencyHeadsUpNotification(
                            context = context,
                            data = alertData,
                            isPoliceRole = true
                        )
                    },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = SosRed),
                    border = ButtonDefaults.outlinedButtonBorder.copy(
                        brush = androidx.compose.ui.graphics.SolidColor(SosRed),
                        width = 1.dp
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = null,
                        tint = SosRed,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(strings.btnTestPoliceSos, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }

            state.reports.forEach { report ->
                SosReportCard(
                    report = report,
                    onAccept = { viewModel.acceptReport(report.id) },
                    onCardClick = {
                        activeSosAlert = SosAlertData(
                            id = report.id,
                            victimName = report.victimName,
                            locationAddress = report.locationAddress,
                            distanceText = "1.8 km",
                            etaText = "6 ${strings.minutesAbbr}",
                            heartRate = report.heartRate,
                            latitude = report.latitude,
                            longitude = report.longitude
                        )
                    }
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
fun SummaryBox(
    title: String,
    count: String,
    color: Color,
    bg: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = bg)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = count, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = color)
            Text(text = title, fontSize = 11.sp, color = TextDark, fontWeight = FontWeight.Medium)
        }
    }
}

@Composable
fun SosReportCard(
    report: SosReport,
    onAccept: () -> Unit,
    onCardClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCardClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardBg),
        border = CardDefaults.outlinedCardBorder().copy(
            brush = androidx.compose.ui.graphics.SolidColor(if (report.isAccepted) StrokeColor else SosRed)
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = if (report.isAccepted) Icons.Default.CheckCircle else Icons.Default.Warning,
                        contentDescription = null,
                        tint = if (report.isAccepted) GreenSafe else SosRed,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = report.victimName,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = TextDark
                    )
                }

                Text(
                    text = report.timestamp,
                    fontSize = 11.sp,
                    color = TextGray
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "${LocalAppStrings.current.locationPrefix}: ${report.locationAddress}",
                fontSize = 12.sp,
                color = TextDark
            )
            Text(
                text = "${LocalAppStrings.current.heartRateTitle}: ${report.heartRate} bpm (${LocalAppStrings.current.criticalSpike})",
                fontSize = 11.sp,
                color = SosRed,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(top = 2.dp)
            )

            Spacer(modifier = Modifier.height(14.dp))

            if (!report.isAccepted) {
                Button(
                    onClick = onAccept,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(42.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Maroon)
                ) {
                    Text(LocalAppStrings.current.btnAcceptHandleReport, fontWeight = FontWeight.Bold, color = White, fontSize = 13.sp)
                }
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(SoftGreen)
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = LocalAppStrings.current.statusReportHandled,
                        color = GreenSafe,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}
