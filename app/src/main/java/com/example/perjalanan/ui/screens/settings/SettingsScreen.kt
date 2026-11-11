package com.example.perjalanan.ui.screens.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Brightness4
import androidx.compose.material.icons.filled.Language
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.perjalanan.data.model.Language
import com.example.perjalanan.data.model.ThemeMode
import com.example.perjalanan.ui.components.PerjalananTopBar
import com.example.perjalanan.ui.theme.CardBg
import com.example.perjalanan.ui.theme.Cream
import com.example.perjalanan.ui.theme.Maroon
import com.example.perjalanan.ui.theme.StrokeColor
import com.example.perjalanan.ui.theme.TextDark
import com.example.perjalanan.ui.theme.TextGray

@Composable
fun SettingsScreen(
    onNavigateBack: () -> Unit,
    viewModel: SettingsViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsState()

    // THEME DIALOG
    if (state.isThemeDialogVisible) {
        AlertDialog(
            onDismissRequest = viewModel::closeThemeDialog,
            shape = RoundedCornerShape(20.dp),
            containerColor = CardBg,
            title = {
                Text(text = "Mode Tampilan", fontWeight = FontWeight.Bold, color = Maroon)
            },
            text = {
                Column {
                    ThemeOptionRow(
                        title = "Terang",
                        selected = state.settings.themeMode == ThemeMode.LIGHT,
                        onClick = { viewModel.selectTheme(ThemeMode.LIGHT) }
                    )
                    ThemeOptionRow(
                        title = "Gelap",
                        selected = state.settings.themeMode == ThemeMode.DARK,
                        onClick = { viewModel.selectTheme(ThemeMode.DARK) }
                    )
                    ThemeOptionRow(
                        title = "Ikuti Sistem",
                        selected = state.settings.themeMode == ThemeMode.SYSTEM,
                        onClick = { viewModel.selectTheme(ThemeMode.SYSTEM) }
                    )
                }
            },
            confirmButton = {},
            dismissButton = {
                TextButton(onClick = viewModel::closeThemeDialog) {
                    Text("Tutup", color = Maroon)
                }
            }
        )
    }

    // LANGUAGE DIALOG
    if (state.isLanguageDialogVisible) {
        AlertDialog(
            onDismissRequest = viewModel::closeLanguageDialog,
            shape = RoundedCornerShape(20.dp),
            containerColor = CardBg,
            title = {
                Text(text = "Ubah Bahasa", fontWeight = FontWeight.Bold, color = Maroon)
            },
            text = {
                Column {
                    ThemeOptionRow(
                        title = "Indonesia",
                        selected = state.settings.language == Language.INDONESIA,
                        onClick = { viewModel.selectLanguage(Language.INDONESIA) }
                    )
                    ThemeOptionRow(
                        title = "English",
                        selected = state.settings.language == Language.ENGLISH,
                        onClick = { viewModel.selectLanguage(Language.ENGLISH) }
                    )
                }
            },
            confirmButton = {},
            dismissButton = {
                TextButton(onClick = viewModel::closeLanguageDialog) {
                    Text("Tutup", color = Maroon)
                }
            }
        )
    }

    Scaffold(
        topBar = {
            PerjalananTopBar(
                title = "Pengaturan",
                onBackClick = onNavigateBack
            )
        },
        containerColor = Cream
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(20.dp)
        ) {
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
                        text = "Preferensi",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Maroon,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    // Theme Row
                    SettingItemRow(
                        icon = Icons.Default.Brightness4,
                        title = "Tema Aplikasi",
                        currentValue = when (state.settings.themeMode) {
                            ThemeMode.LIGHT -> "Terang"
                            ThemeMode.DARK -> "Gelap"
                            ThemeMode.SYSTEM -> "Ikuti Sistem"
                        },
                        onClick = viewModel::openThemeDialog
                    )

                    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = StrokeColor)

                    // Language Row
                    SettingItemRow(
                        icon = Icons.Default.Language,
                        title = "Bahasa",
                        currentValue = when (state.settings.language) {
                            Language.INDONESIA -> "Indonesia"
                            Language.ENGLISH -> "English"
                        },
                        onClick = viewModel::openLanguageDialog
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "PERJALANAN v1.0.0 (Compose Edition)",
                fontSize = 11.sp,
                color = TextGray,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Text(
                text = "Dilindungi dengan enkripsi sinyal darurat modern.",
                fontSize = 11.sp,
                color = TextGray,
                modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 2.dp)
            )
        }
    }
}

@Composable
fun SettingItemRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    currentValue: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = icon, contentDescription = null, tint = Maroon, modifier = Modifier.size(22.dp))
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = TextDark,
                modifier = Modifier.padding(start = 12.dp)
            )
        }
        Text(
            text = currentValue,
            fontSize = 13.sp,
            color = Maroon,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun ThemeOptionRow(
    title: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = onClick,
            colors = RadioButtonDefaults.colors(selectedColor = Maroon)
        )
        Text(
            text = title,
            fontSize = 14.sp,
            color = TextDark,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}
