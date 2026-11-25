package com.example.perjalanan.ui.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.perjalanan.R
import com.example.perjalanan.ui.localization.LocalAppStrings
import com.example.perjalanan.ui.theme.*

@Composable
fun RequestPoliceSentScreen(
    onBackToRedeemInvite: () -> Unit,
    onContinueAsUser: () -> Unit
) {
    val strings = LocalAppStrings.current

    Box(
        modifier = Modifier.fillMaxSize().background(Cream)
    ) {
        Image(
            painter = painterResource(R.drawable.bg_wave_top),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.fillMaxWidth().height(110.dp).align(Alignment.TopCenter)
        )
        Image(
            painter = painterResource(R.drawable.bg_wave_bottom),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.fillMaxWidth().height(150.dp).align(Alignment.BottomCenter)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // IKON SUKSES
            Box(
                modifier = Modifier.size(100.dp).clip(CircleShape).background(SoftPeach),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = Maroon,
                    modifier = Modifier.size(56.dp)
                )
            }

            Spacer(Modifier.height(24.dp))

            Text(
                text = strings.requestSentTitle,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Maroon,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(12.dp))

            Text(
                text = strings.requestSentDesc,
                fontSize = 14.sp,
                color = TextDark,
                textAlign = TextAlign.Center,
                lineHeight = 21.sp
            )

            Spacer(Modifier.height(28.dp))

            // INFO CEK EMAIL
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(CardBg)
                    .padding(16.dp)
            ) {
                Row(verticalAlignment = Alignment.Top) {
                    Icon(Icons.Default.Email, contentDescription = null, tint = GoldBadge, modifier = Modifier.size(20.dp))
                    Spacer(Modifier.width(10.dp))
                    Text(
                        text = strings.requestSentEmailHint,
                        fontSize = 13.sp,
                        color = TextGray,
                        lineHeight = 18.sp
                    )
                }
            }

            Spacer(Modifier.height(32.dp))

            // TOMBOL SUDAH PUNYA KODE
            Button(
                onClick = onBackToRedeemInvite,
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Maroon)
            ) {
                Text(strings.btnIHaveCode, color = White, fontSize = 15.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(Modifier.height(12.dp))

            // TOMBOL LANJUT SEBAGAI WARGA
            OutlinedButton(
                onClick = onContinueAsUser,
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.outlinedButtonColors(containerColor = White)
            ) {
                Text(strings.btnSkipAsCitizen, color = TextGray, fontSize = 14.sp, fontWeight = FontWeight.Medium)
            }
        }
    }
}
