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
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.LocalPolice
import androidx.compose.material.icons.filled.LockReset
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.perjalanan.ui.components.LogoutConfirmDialog
import com.example.perjalanan.ui.components.PerjalananTopBar
import com.example.perjalanan.ui.components.PoliceBottomBar
import com.example.perjalanan.ui.localization.LocalAppStrings
import com.example.perjalanan.ui.navigation.Screen
import com.example.perjalanan.ui.screens.profile.ProfileDetailRow
import com.example.perjalanan.ui.screens.profile.ProfileMenuRow
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
fun PoliceProfileScreen(
    onNavigateBack: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToChangePassword: () -> Unit,
    onLogoutSuccess: () -> Unit,
    onNavigateToDashboard: () -> Unit = {},
    onNavigateToMap: () -> Unit = {},
    viewModel: PoliceProfileViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val strings = LocalAppStrings.current

    LaunchedEffect(state.toastMessage) {
        state.toastMessage?.let { msg ->
            snackbarHostState.showSnackbar(msg)
            viewModel.clearToast()
        }
    }

    LaunchedEffect(state.isLoggedOut) {
        if (state.isLoggedOut) {
            onLogoutSuccess()
        }
    }

    if (state.isLogoutDialogOpen) {
        LogoutConfirmDialog(
            onDismiss = viewModel::closeLogoutDialog,
            onConfirm = viewModel::logout
        )
    }

    if (state.isAlarmSettingsOpen) {
        AlertDialog(
            onDismissRequest = viewModel::closeAlarmSettings,
            shape = RoundedCornerShape(20.dp),
            containerColor = CardBg,
            title = {
                Text(text = "Pengaturan Alarm SOS", fontWeight = FontWeight.Bold, color = Maroon)
            },
            text = {
                Column {
                    Text(
                        text = "Status Alarm Saat Ini: ${if (state.officer.isAlarmActive) "AKTIF" else "NONAKTIF"}",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = if (state.officer.isAlarmActive) SosRed else TextGray,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                    Text(
                        text = "Pilih aksi pengontrolan sirine darurat:",
                        fontSize = 12.sp,
                        color = TextDark,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = "• Aktifkan Sirine Alarm",
                        fontSize = 14.sp,
                        color = Maroon,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { viewModel.setAlarm(true) }
                            .padding(vertical = 8.dp)
                    )
                    Text(
                        text = "• Matikan Sirine Alarm",
                        fontSize = 14.sp,
                        color = SosRed,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { viewModel.setAlarm(false) }
                            .padding(vertical = 8.dp)
                    )
                }
            },
            confirmButton = {},
            dismissButton = {
                TextButton(onClick = viewModel::closeAlarmSettings) {
                    Text("Tutup", color = Maroon)
                }
            }
        )
    }

    Scaffold(
        topBar = {
            PerjalananTopBar(
                title = strings.policeProfileTitle,
                onBackClick = onNavigateBack,
                actions = {
                    IconButton(onClick = onNavigateToSettings) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = strings.settingsTitle,
                            tint = White
                        )
                    }
                }
            )
        },
        bottomBar = {
            PoliceBottomBar(
                currentRoute = Screen.PoliceProfile.route,
                onNavigate = { screen ->
                    when (screen) {
                        Screen.PoliceDashboard -> onNavigateToDashboard()
                        Screen.PoliceMap -> onNavigateToMap()
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
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            Box(
                modifier = Modifier
                    .size(96.dp)
                    .clip(CircleShape)
                    .background(Peach)
                    .border(2.dp, Maroon, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.LocalPolice,
                    contentDescription = null,
                    tint = Maroon,
                    modifier = Modifier.size(54.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = state.officer.name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Maroon
            )

            Text(
                text = state.officer.rank,
                fontSize = 12.sp,
                color = TextGray,
                letterSpacing = 1.sp,
                modifier = Modifier.padding(top = 2.dp)
            )

            Row(
                modifier = Modifier.padding(top = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(SoftGreen)
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "● ONLINE",
                        color = GreenSafe,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(SoftPeach)
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "Status: ${state.officer.dutyStatusText}",
                        color = Maroon,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // OFFICER DETAILS CARD
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = CardBg),
                border = CardDefaults.outlinedCardBorder().copy(
                    brush = androidx.compose.ui.graphics.SolidColor(StrokeColor)
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = strings.dutyInfo,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Maroon,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    ProfileDetailRow(icon = Icons.Default.Badge, label = strings.idNumber, value = state.officer.id)
                    HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp), color = StrokeColor)
                    ProfileDetailRow(icon = Icons.Default.Email, label = "Email", value = state.officer.email)
                    HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp), color = StrokeColor)
                    ProfileDetailRow(icon = Icons.Default.Phone, label = "Telepon", value = state.officer.phone)
                    HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp), color = StrokeColor)
                    ProfileDetailRow(icon = Icons.Default.Security, label = strings.dutyUnit, value = state.officer.unit)
                    HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp), color = StrokeColor)
                    ProfileDetailRow(icon = Icons.Default.Business, label = strings.headquarters, value = state.officer.office)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ALARM & ACCOUNT ACTIONS
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = CardBg),
                border = CardDefaults.outlinedCardBorder().copy(
                    brush = androidx.compose.ui.graphics.SolidColor(StrokeColor)
                )
            ) {
                Column(modifier = Modifier.padding(8.dp)) {
                    ProfileMenuRow(
                        icon = Icons.Default.Notifications,
                        iconBg = SoftRed,
                        iconTint = SosRed,
                        title = strings.sosSirenAlarm,
                        subtitle = "Status: ${if (state.officer.isAlarmActive) strings.statusOnline else strings.statusOffline}",
                        onClick = viewModel::openAlarmSettings
                    )

                    HorizontalDivider(modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp), color = StrokeColor)

                    ProfileMenuRow(
                        icon = Icons.Default.Settings,
                        iconBg = SoftPeach,
                        iconTint = Maroon,
                        title = strings.appSettingsMenu,
                        subtitle = strings.appSettingsSub,
                        onClick = onNavigateToSettings
                    )

                    HorizontalDivider(modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp), color = StrokeColor)

                    ProfileMenuRow(
                        icon = Icons.Default.LockReset,
                        iconBg = SoftPeach,
                        iconTint = Maroon,
                        title = strings.changePasswordMenu,
                        subtitle = strings.changePasswordSub,
                        onClick = onNavigateToChangePassword
                    )

                    HorizontalDivider(modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp), color = StrokeColor)

                    ProfileMenuRow(
                        icon = Icons.Default.ExitToApp,
                        iconBg = SoftRed,
                        iconTint = SosRed,
                        title = strings.logoutMenu,
                        subtitle = strings.logoutSub,
                        onClick = viewModel::openLogoutDialog
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}
