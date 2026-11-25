package com.example.perjalanan.ui.screens.auth

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MarkEmailRead
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.perjalanan.ui.components.PerjalananTopBar
import com.example.perjalanan.ui.localization.LocalAppStrings
import com.example.perjalanan.ui.theme.LocalAppColors
import com.example.perjalanan.ui.theme.CardBg
import com.example.perjalanan.ui.theme.Cream
import com.example.perjalanan.ui.theme.Maroon
import com.example.perjalanan.ui.theme.Peach
import com.example.perjalanan.ui.theme.SosRed
import com.example.perjalanan.ui.theme.StrokeColor
import com.example.perjalanan.ui.theme.TextDark
import com.example.perjalanan.ui.theme.TextGray
import com.example.perjalanan.ui.theme.White

@Composable
fun VerifyCodeScreen(
    identifier: String = "",
    destination: String = "",
    onNavigateBack: () -> Unit,
    onNavigateToChangePassword: (resetToken: String) -> Unit,
    viewModel: VerifyCodeViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val focusRequesters = remember { List(4) { FocusRequester() } }
    val strings = LocalAppStrings.current
    val colors = LocalAppColors.current

    LaunchedEffect(state.isSuccess) {
        if (state.isSuccess) {
            onNavigateToChangePassword(state.resetToken ?: "")
            viewModel.resetState()
        }
    }

    Scaffold(
        topBar = {
            PerjalananTopBar(
                title = strings.verifyCodeTitle,
                onBackClick = onNavigateBack
            )
        },
        containerColor = Cream
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // Icon Header
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(Peach),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.MarkEmailRead,
                    contentDescription = null,
                    tint = Maroon,
                    modifier = Modifier.size(42.dp)
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = strings.enterOtpTitle,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Maroon
            )

            val displayDesc = if (destination.isNotBlank()) {
                "Kami telah mengirimkan 4 digit kode verifikasi ke $destination"
            } else {
                strings.otpSentDesc
            }

            Text(
                text = displayDesc,
                fontSize = 13.sp,
                color = TextGray,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // 4 OTP Boxes
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                for (i in 0 until 4) {
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 6.dp)
                            .size(56.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(CardBg)
                            .border(
                                width = 1.5.dp,
                                color = if (state.otp[i].isNotEmpty()) Maroon else StrokeColor,
                                shape = RoundedCornerShape(12.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        BasicTextField(
                            value = state.otp[i],
                            onValueChange = { value ->
                                viewModel.onOtpDigitChange(i, value)
                                if (value.isNotEmpty() && i < 3) {
                                    focusRequesters[i + 1].requestFocus()
                                }
                            },
                            modifier = Modifier.focusRequester(focusRequesters[i]),
                            textStyle = androidx.compose.ui.text.TextStyle(
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextDark,
                                textAlign = TextAlign.Center
                            ),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            singleLine = true
                        )
                    }
                }
            }

            if (state.errorMessage != null) {
                Text(
                    text = state.errorMessage ?: "",
                    color = SosRed,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 12.dp)
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            // Resend Timer
            if (state.isTimerActive) {
                Text(
                    text = "${strings.resendCodeTimer} (00:${state.secondsRemaining.toString().padStart(2, '0')})",
                    color = TextGray,
                    fontSize = 13.sp
                )
            } else {
                Text(
                    text = strings.resendCode,
                    color = Maroon,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    modifier = Modifier.clickable { viewModel.resend(identifier) }
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Verify Button
            Button(
                onClick = { viewModel.verify(identifier) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                enabled = !state.isLoading,
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Maroon)
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp), color = White, strokeWidth = 2.dp)
                } else {
                    Text(text = strings.btnVerify, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = White)
                }
            }
        }
    }
}
