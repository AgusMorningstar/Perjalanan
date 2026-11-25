package com.example.perjalanan.ui.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.perjalanan.R
import com.example.perjalanan.ui.localization.LocalAppStrings
import com.example.perjalanan.ui.theme.*

@Composable
fun RequestPoliceScreen(
    onNavigateBack: () -> Unit,
    onRequestSent: () -> Unit,
    viewModel: RequestPoliceViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val focusManager = LocalFocusManager.current
    val strings = LocalAppStrings.current

    // Navigasi setelah sukses
    LaunchedEffect(state.isSuccess) {
        if (state.isSuccess) onRequestSent()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Cream)
    ) {
        // WAVE ATAS
        Image(
            painter = painterResource(R.drawable.bg_wave_top),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.fillMaxWidth().height(110.dp).align(Alignment.TopCenter)
        )
        // WAVE BAWAH
        Image(
            painter = painterResource(R.drawable.bg_wave_bottom),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.fillMaxWidth().height(150.dp).align(Alignment.BottomCenter)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(90.dp))

            // Tombol kembali (kiri)
            Row(Modifier.fillMaxWidth()) {
                IconButton(onClick = onNavigateBack) {
                    Icon(Icons.Default.ArrowBack, contentDescription = strings.back, tint = Maroon)
                }
            }

            // IKON
            Box(
                modifier = Modifier.size(78.dp).clip(CircleShape).background(SoftPeach),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Badge,
                    contentDescription = null,
                    tint = Maroon,
                    modifier = Modifier.size(42.dp)
                )
            }

            Spacer(Modifier.height(16.dp))

            Text(
                text = strings.requestPoliceTitle,
                fontSize = 21.sp,
                fontWeight = FontWeight.Bold,
                color = Maroon,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(6.dp))
            Text(
                text = strings.requestPoliceSubtitle,
                fontSize = 13.sp,
                color = TextDark,
                textAlign = TextAlign.Center,
                lineHeight = 19.sp,
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            Spacer(Modifier.height(24.dp))

            // INFO CARD
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(CardBg)
                    .padding(14.dp)
            ) {
                Row(verticalAlignment = Alignment.Top) {
                    Icon(Icons.Default.Info, contentDescription = null, tint = GoldBadge, modifier = Modifier.size(18.dp).padding(top = 1.dp))
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = strings.requestPoliceInfo,
                        fontSize = 12.sp,
                        color = TextGray,
                        lineHeight = 17.sp
                    )
                }
            }

            Spacer(Modifier.height(20.dp))

            // ERROR ALERT
            if (state.errorMessage != null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(SoftRed)
                        .padding(horizontal = 14.dp, vertical = 10.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Warning, contentDescription = null, tint = SosRed, modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(8.dp))
                        Text(state.errorMessage ?: "", color = SosRed, fontSize = 12.sp, fontWeight = FontWeight.Medium)
                    }
                }
            }

            // FORM FIELDS
            // Nama Polsek
            OutlinedTextField(
                value = state.polsekName,
                onValueChange = viewModel::onPolsekNameChange,
                modifier = Modifier.fillMaxWidth(),
                label = { Text(strings.requestPolsekLabel) },
                placeholder = { Text(strings.requestPolsekPlaceholder, color = TextGray) },
                leadingIcon = { Icon(Icons.Default.LocationCity, contentDescription = null, tint = Maroon) },
                shape = RoundedCornerShape(14.dp),
                colors = fieldColors(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) })
            )

            Spacer(Modifier.height(12.dp))

            // Nama Lengkap
            OutlinedTextField(
                value = state.fullName,
                onValueChange = viewModel::onFullNameChange,
                modifier = Modifier.fillMaxWidth(),
                label = { Text(strings.requestFullNameLabel) },
                placeholder = { Text(strings.requestFullNamePlaceholder, color = TextGray) },
                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = Maroon) },
                shape = RoundedCornerShape(14.dp),
                colors = fieldColors(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) })
            )

            Spacer(Modifier.height(12.dp))

            // NRP / NIP
            OutlinedTextField(
                value = state.nrp,
                onValueChange = viewModel::onNrpChange,
                modifier = Modifier.fillMaxWidth(),
                label = { Text(strings.requestNrpLabel) },
                placeholder = { Text(strings.requestNrpPlaceholder, color = TextGray) },
                leadingIcon = { Icon(Icons.Default.Badge, contentDescription = null, tint = Maroon) },
                shape = RoundedCornerShape(14.dp),
                colors = fieldColors(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) })
            )

            Spacer(Modifier.height(12.dp))

            // Nomor HP (opsional)
            OutlinedTextField(
                value = state.phone,
                onValueChange = viewModel::onPhoneChange,
                modifier = Modifier.fillMaxWidth(),
                label = { Text(strings.requestPhoneLabel) },
                placeholder = { Text(strings.requestPhonePlaceholder, color = TextGray) },
                leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null, tint = Maroon) },
                shape = RoundedCornerShape(14.dp),
                colors = fieldColors(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone, imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) })
            )

            Spacer(Modifier.height(12.dp))

            // Alasan (opsional)
            OutlinedTextField(
                value = state.reason,
                onValueChange = viewModel::onReasonChange,
                modifier = Modifier.fillMaxWidth().height(100.dp),
                label = { Text(strings.requestReasonLabel) },
                placeholder = { Text(strings.requestReasonPlaceholder, color = TextGray) },
                leadingIcon = { Icon(Icons.Default.Notes, contentDescription = null, tint = Maroon) },
                shape = RoundedCornerShape(14.dp),
                colors = fieldColors(),
                maxLines = 4,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = {
                    focusManager.clearFocus()
                    viewModel.submitRequest()
                })
            )

            Spacer(Modifier.height(24.dp))

            // TOMBOL KIRIM
            Button(
                onClick = {
                    focusManager.clearFocus()
                    viewModel.submitRequest()
                },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Maroon),
                enabled = !state.isLoading && state.polsekName.isNotBlank() && state.fullName.isNotBlank() && state.nrp.isNotBlank()
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(color = White, strokeWidth = 2.dp, modifier = Modifier.size(24.dp))
                } else {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                        Icon(Icons.Default.Send, contentDescription = null, tint = White, modifier = Modifier.size(20.dp))
                        Spacer(Modifier.width(8.dp))
                        Text(strings.btnSendPoliceRequest, color = White, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(Modifier.height(100.dp))
        }
    }
}

@Composable
private fun fieldColors() = OutlinedTextFieldDefaults.colors(
    focusedContainerColor   = CardBg,
    unfocusedContainerColor = CardBg,
    focusedBorderColor      = Maroon,
    unfocusedBorderColor    = StrokeColor,
    focusedLabelColor       = Maroon,
    unfocusedLabelColor     = TextDark,
    focusedTextColor        = TextPrimary,
    unfocusedTextColor      = TextPrimary
)
