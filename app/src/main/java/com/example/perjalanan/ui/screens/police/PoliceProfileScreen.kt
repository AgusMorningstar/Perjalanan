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
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockReset
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
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
import com.example.perjalanan.ui.components.ChangePasswordProfileDialog
import com.example.perjalanan.ui.components.EditProfileFieldDialog
import com.example.perjalanan.ui.components.EditableFieldType
import com.example.perjalanan.ui.components.LogoutConfirmDialog
import com.example.perjalanan.ui.components.PerjalananTopBar
import com.example.perjalanan.ui.components.PoliceBottomBar
import com.example.perjalanan.ui.localization.LocalAppStrings
import com.example.perjalanan.ui.theme.LocalAppColors
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
    onNavigateBack: () -> Unit = {},
    onNavigateToDashboard: () -> Unit = {},
    onNavigateToMap: () -> Unit = {},
    onNavigateToSettings: () -> Unit = {},
    onNavigateToChangePassword: () -> Unit = {},
    onLogoutSuccess: () -> Unit = {},
    viewModel: PoliceProfileViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val strings = LocalAppStrings.current
    val colors = LocalAppColors.current
    val snackbarHostState = remember { SnackbarHostState() }

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

    // Dialog Edit Field (Nama, Email, Telepon)
    state.editingField?.let { fieldType ->
        val initialVal = when (fieldType) {
            EditableFieldType.NAME -> state.officer.name
            EditableFieldType.EMAIL -> state.officer.email
            EditableFieldType.PHONE -> state.officer.phone
        }
        EditProfileFieldDialog(
            fieldType = fieldType,
            initialValue = initialVal,
            isLoading = state.isDialogLoading,
            errorMessage = state.dialogErrorMessage,
            onDismiss = viewModel::closeEditFieldDialog,
            onConfirm = viewModel::submitFieldUpdate
        )
    }

    // Dialog Ubah Password
    if (state.isChangePasswordDialogOpen) {
        ChangePasswordProfileDialog(
            isLoading = state.isDialogLoading,
            errorMessage = state.dialogErrorMessage,
            onDismiss = viewModel::closeChangePasswordDialog,
            onConfirm = viewModel::submitPasswordChange
        )
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
                Text(text = strings.alarmSettingsTitle, fontWeight = FontWeight.Bold, color = Maroon)
            },
            text = {
                Column {
                    Text(
                        text = "${strings.currentAlarmStatusLabel} ${if (state.officer.isAlarmActive) strings.statusActive else strings.statusInactive}",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = if (state.officer.isAlarmActive) SosRed else TextGray,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                    Text(
                        text = strings.alarmControlAction,
                        fontSize = 12.sp,
                        color = TextDark,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = strings.activateAlarmAction,
                        fontSize = 14.sp,
                        color = Maroon,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { viewModel.setAlarm(true) }
                            .padding(vertical = 8.dp)
                    )
                    Text(
                        text = strings.deactivateAlarmAction,
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
                    Text(strings.close, color = Maroon)
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
                        text = "Status: ${if (state.officer.isDutyActive) strings.policeDutyStatusStandby else "-"}",
                        color = Maroon,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // OFFICER DETAILS CARD (DENGAN IKON PENSIL UNTUK DATA AKUN)
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

                    // 1. Nama Lengkap (Editable dengan pensil)
                    ProfileDetailRow(
                        icon = Icons.Default.Person,
                        label = strings.fullName,
                        value = state.officer.name,
                        onEditClick = { viewModel.openEditFieldDialog(EditableFieldType.NAME) }
                    )
                    HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp), color = StrokeColor)

                    // 2. Email (Editable dengan pensil)
                    ProfileDetailRow(
                        icon = Icons.Default.Email,
                        label = strings.emailLabel,
                        value = state.officer.email,
                        onEditClick = { viewModel.openEditFieldDialog(EditableFieldType.EMAIL) }
                    )
                    HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp), color = StrokeColor)

                    // 3. Telepon (Editable dengan pensil)
                    ProfileDetailRow(
                        icon = Icons.Default.Phone,
                        label = strings.phoneLabel,
                        value = state.officer.phone.ifBlank { strings.notSet },
                        onEditClick = { viewModel.openEditFieldDialog(EditableFieldType.PHONE) }
                    )
                    HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp), color = StrokeColor)

                    // 4. Password (Editable dengan pensil, butuh password lama)
                    ProfileDetailRow(
                        icon = Icons.Default.Lock,
                        label = strings.passwordLabel,
                        value = "••••••••",
                        onEditClick = { viewModel.openChangePasswordDialog() }
                    )
                    HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp), color = StrokeColor)

                    // 5. ID Anggota / NRP (Read-only, tanpa pensil)
                    ProfileDetailRow(
                        icon = Icons.Default.Badge,
                        label = strings.idNumber,
                        value = state.officer.id,
                        onEditClick = null
                    )
                    HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp), color = StrokeColor)

                    // 6. Satuan Tugas (Read-only, tanpa pensil)
                    ProfileDetailRow(
                        icon = Icons.Default.Security,
                        label = strings.dutyUnit,
                        value = state.officer.unit,
                        onEditClick = null
                    )
                    HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp), color = StrokeColor)

                    // 7. Kantor Polsek (Read-only, tanpa pensil)
                    ProfileDetailRow(
                        icon = Icons.Default.Business,
                        label = strings.headquarters,
                        value = state.officer.office,
                        onEditClick = null
                    )
                    HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp), color = StrokeColor)

                    // 8. Role (TIDAK ADA PENSIL - role polisi terikat kode undangan polsek)
                    ProfileDetailRow(
                        icon = Icons.Default.Security,
                        label = strings.roleLabel,
                        value = strings.rolePoliceText,
                        onEditClick = null
                    )
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
                        onClick = viewModel::openChangePasswordDialog
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
