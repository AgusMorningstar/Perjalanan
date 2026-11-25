package com.example.perjalanan.ui.screens.profile

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
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.perjalanan.ui.components.ChangePasswordProfileDialog
import com.example.perjalanan.ui.components.EditProfileFieldDialog
import com.example.perjalanan.ui.components.EditableFieldType
import com.example.perjalanan.ui.components.LogoutConfirmDialog
import com.example.perjalanan.ui.components.PerjalananBottomBar
import com.example.perjalanan.ui.components.PerjalananTopBar
import com.example.perjalanan.ui.localization.LocalAppStrings
import com.example.perjalanan.ui.theme.LocalAppColors
import com.example.perjalanan.ui.navigation.Screen
import com.example.perjalanan.ui.theme.CardBg
import com.example.perjalanan.ui.theme.Cream
import com.example.perjalanan.ui.theme.GoldBadge
import com.example.perjalanan.ui.theme.Maroon
import com.example.perjalanan.ui.theme.Peach
import com.example.perjalanan.ui.theme.SoftPeach
import com.example.perjalanan.ui.theme.SoftRed
import com.example.perjalanan.ui.theme.SosRed
import com.example.perjalanan.ui.theme.StrokeColor
import com.example.perjalanan.ui.theme.TextDark
import com.example.perjalanan.ui.theme.TextGray

@Composable
fun ProfileScreen(
    onNavigate: (Screen) -> Unit,
    onLogoutSuccess: () -> Unit,
    viewModel: ProfileViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val strings = LocalAppStrings.current
    val colors = LocalAppColors.current
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state.isLoggedOut) {
        if (state.isLoggedOut) {
            onLogoutSuccess()
        }
    }

    LaunchedEffect(state.snackbarMessage) {
        state.snackbarMessage?.let { msg ->
            snackbarHostState.showSnackbar(msg)
            viewModel.clearSnackbar()
        }
    }

    // Dialog Edit Field (Nama, Email, Telepon)
    state.editingField?.let { fieldType ->
        val initialVal = when (fieldType) {
            EditableFieldType.NAME -> state.user.name
            EditableFieldType.EMAIL -> state.user.email
            EditableFieldType.PHONE -> state.user.phone
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

    Scaffold(
        topBar = {
            PerjalananTopBar(title = strings.userProfileTitle)
        },
        bottomBar = {
            PerjalananBottomBar(
                currentRoute = Screen.Profile.route,
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
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            // Avatar & Name
            Box(
                modifier = Modifier
                    .size(96.dp)
                    .clip(CircleShape)
                    .background(Peach)
                    .border(2.dp, Maroon, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    tint = Maroon,
                    modifier = Modifier.size(54.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = state.user.name.ifBlank { strings.defaultUserName },
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Maroon
            )

            Box(
                modifier = Modifier
                    .padding(top = 4.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(GoldBadge)
                    .padding(horizontal = 12.dp, vertical = 4.dp)
            ) {
                Text(
                    text = if (state.user.isPolice) strings.rolePoliceText else strings.roleUserText,
                    color = TextDark,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // PROFILE DETAILS CARD DENGAN IKON PENSIL
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
                        text = strings.accountInfo,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Maroon,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    // 1. Nama Lengkap (Bisa diedit via pensil)
                    ProfileDetailRow(
                        icon = Icons.Default.Person,
                        label = strings.fullName,
                        value = state.user.name.ifBlank { "-" },
                        onEditClick = { viewModel.openEditFieldDialog(EditableFieldType.NAME) }
                    )
                    HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp), color = StrokeColor)

                    // 2. Email (Bisa diedit via pensil)
                    ProfileDetailRow(
                        icon = Icons.Default.Email,
                        label = strings.emailLabel,
                        value = state.user.email.ifBlank { "-" },
                        onEditClick = { viewModel.openEditFieldDialog(EditableFieldType.EMAIL) }
                    )
                    HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp), color = StrokeColor)

                    // 3. Telepon (Bisa diedit via pensil)
                    ProfileDetailRow(
                        icon = Icons.Default.Phone,
                        label = strings.phoneLabel,
                        value = state.user.phone.ifBlank { strings.notSet },
                        onEditClick = { viewModel.openEditFieldDialog(EditableFieldType.PHONE) }
                    )
                    HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp), color = StrokeColor)

                    // 4. Password (Bisa diedit via pensil, butuh password lama)
                    ProfileDetailRow(
                        icon = Icons.Default.Lock,
                        label = strings.passwordLabel,
                        value = "••••••••",
                        onEditClick = { viewModel.openChangePasswordDialog() }
                    )
                    HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp), color = StrokeColor)

                    // 5. Role (TIDAK ADA PENSIL - hanya bisa diubah via kode undangan admin)
                    ProfileDetailRow(
                        icon = Icons.Default.Security,
                        label = strings.roleLabel,
                        value = if (state.user.isPolice) strings.rolePoliceText else strings.roleUserText,
                        onEditClick = null // Tanpa pensil!
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // MENU SETTINGS & LOGOUT CARD
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
                        icon = Icons.Default.Settings,
                        iconBg = SoftPeach,
                        iconTint = Maroon,
                        title = strings.appSettingsMenu,
                        subtitle = strings.appSettingsSub,
                        onClick = { onNavigate(Screen.Settings) }
                    )

                    // Tombol Tukar Kode Undangan Polsek (hanya tampil jika role masih warga)
                    if (!state.user.isPolice) {
                        HorizontalDivider(modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp), color = StrokeColor)

                        ProfileMenuRow(
                            icon = Icons.Default.Shield,
                            iconBg = SoftPeach,
                            iconTint = Maroon,
                            title = strings.inviteCodeMenuTitle,
                            subtitle = strings.inviteCodeMenuSub,
                            onClick = { onNavigate(Screen.RedeemInvite) }
                        )
                    }

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

@Composable
fun ProfileDetailRow(
    icon: ImageVector,
    label: String,
    value: String,
    onEditClick: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = TextGray,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(text = label, fontSize = 11.sp, color = TextGray)
                Text(
                    text = value,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = TextDark
                )
            }
        }

        // Tampilkan ikon pensil jika editable
        if (onEditClick != null) {
            IconButton(
                onClick = onEditClick,
                modifier = Modifier.size(32.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit $label",
                    tint = Maroon,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

@Composable
fun ProfileMenuRow(
    icon: ImageVector,
    iconBg: androidx.compose.ui.graphics.Color,
    iconTint: androidx.compose.ui.graphics.Color,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(CircleShape)
                    .background(iconBg),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(text = title, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextDark)
                Text(text = subtitle, fontSize = 11.sp, color = TextGray)
            }
        }

        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
            contentDescription = null,
            tint = TextGray,
            modifier = Modifier.size(14.dp)
        )
    }
}
