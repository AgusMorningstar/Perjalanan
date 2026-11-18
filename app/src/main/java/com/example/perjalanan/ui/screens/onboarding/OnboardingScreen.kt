package com.example.perjalanan.ui.screens.onboarding

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Watch
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.example.perjalanan.R
import com.example.perjalanan.ui.theme.CardBg
import com.example.perjalanan.ui.theme.Cream
import com.example.perjalanan.ui.theme.GoldBadge
import com.example.perjalanan.ui.theme.Maroon
import com.example.perjalanan.ui.theme.Peach
import com.example.perjalanan.ui.theme.SoftPeach
import com.example.perjalanan.ui.theme.SosRed
import com.example.perjalanan.ui.theme.StrokeColor
import com.example.perjalanan.ui.theme.TextDark
import com.example.perjalanan.ui.theme.TextGray
import com.example.perjalanan.ui.theme.White
import kotlinx.coroutines.launch

@Composable
fun OnboardingScreen(
    onFinishOnboarding: () -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { 3 })
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Cream)
    ) {
        // Hiasan Gelombang Atas (Top Wave)
        Image(
            painter = painterResource(id = R.drawable.bg_wave_top),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .height(68.dp)
                .align(Alignment.TopCenter)
        )

        // Hiasan Gelombang Bawah (Bottom Wave)
        Image(
            painter = painterResource(id = R.drawable.bg_wave_bottom),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .align(Alignment.BottomCenter)
        )

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Header: Logo + Judul + Tombol Lewati
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .padding(top = 56.dp, bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Logo kecil
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(Peach),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logomaroon),
                        contentDescription = "Logo Perjalanan",
                        modifier = Modifier.size(22.dp)
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = stringResource(id = R.string.ob_logo_text),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = Maroon,
                    letterSpacing = 2.sp,
                    modifier = Modifier.weight(1f)
                )

                // Tombol Lewati (Skip)
                Text(
                    text = stringResource(id = R.string.ob_skip),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Maroon,
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .clickable { onFinishOnboarding() }
                        .padding(horizontal = 8.dp, vertical = 6.dp)
                )
            }

            // Pager Konten Onboarding
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) { page ->
                when (page) {
                    0 -> OnboardingPageOne()
                    1 -> OnboardingPageTwo()
                    2 -> OnboardingPageThree()
                }
            }

            // Indikator Titik (Dots)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, bottom = 12.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(3) { index ->
                    val isActive = pagerState.currentPage == index
                    val dotWidth by animateDpAsState(
                        targetValue = if (isActive) 24.dp else 9.dp,
                        label = "dotWidth"
                    )
                    val dotColor by animateColorAsState(
                        targetValue = if (isActive) Maroon else Peach,
                        label = "dotColor"
                    )

                    Box(
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .height(9.dp)
                            .width(dotWidth)
                            .clip(RoundedCornerShape(5.dp))
                            .background(dotColor)
                    )
                }
            }

            // Tombol Lanjut / Mulai Sekarang
            Button(
                onClick = {
                    if (pagerState.currentPage < 2) {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    } else {
                        onFinishOnboarding()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 68.dp)
                    .height(52.dp),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Maroon),
                contentPadding = PaddingValues(horizontal = 24.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = if (pagerState.currentPage < 2)
                            stringResource(id = R.string.ob_next)
                        else
                            stringResource(id = R.string.ob_start),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = White
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        tint = White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

// =========================================================================
// HALAMAN 1: SELAMAT DATANG / PERJALANAN AMAN
// =========================================================================
@Composable
fun OnboardingPageOne() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Balon Ucapan
        SpeechBubble(
            text = stringResource(id = R.string.ob1_bubble),
            tailEndPadding = 64.dp,
            isTailOnRight = true
        )

        Spacer(modifier = Modifier.height(14.dp))

        // Area Maskot "Si Jaga"
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            MascotIllustration(page = 1)
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Teks Deskripsi
        Text(
            text = stringResource(id = R.string.ob1_tag),
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = Maroon,
            letterSpacing = 1.5.sp
        )

        Text(
            text = stringResource(id = R.string.ob1_title),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Maroon,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 6.dp)
        )

        Text(
            text = stringResource(id = R.string.ob1_body),
            fontSize = 14.sp,
            color = TextDark,
            textAlign = TextAlign.Center,
            lineHeight = 20.sp,
            modifier = Modifier.padding(top = 8.dp, bottom = 12.dp)
        )
    }
}

// =========================================================================
// HALAMAN 2: SENSOR CERDAS (DETAK JANTUNG & GERAK)
// =========================================================================
@Composable
fun OnboardingPageTwo() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Balon Ucapan
        SpeechBubble(
            text = stringResource(id = R.string.ob2_bubble),
            tailEndPadding = 90.dp,
            isTailOnRight = true
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Area Maskot + Kartu Sensor
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Maskot (Disesuaikan agar ada ruang vertikal cukup untuk kartu)
                MascotIllustration(page = 2, size = 125.dp)

                Spacer(modifier = Modifier.height(10.dp))

                // Kartu Fitur Sensor Kembar (Wrap content tinggi & seimbang)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Min),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    SensorFeatureCard(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        icon = Icons.Default.Favorite,
                        title = stringResource(id = R.string.ob2_card1_title),
                        desc = stringResource(id = R.string.ob2_card1_desc)
                    )
                    SensorFeatureCard(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        icon = Icons.Default.DirectionsRun,
                        title = stringResource(id = R.string.ob2_card2_title),
                        desc = stringResource(id = R.string.ob2_card2_desc)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        // Teks Deskripsi
        Text(
            text = stringResource(id = R.string.ob2_tag),
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = Maroon,
            letterSpacing = 1.5.sp
        )

        Text(
            text = stringResource(id = R.string.ob2_title),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Maroon,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 6.dp)
        )

        Text(
            text = stringResource(id = R.string.ob2_body),
            fontSize = 14.sp,
            color = TextDark,
            textAlign = TextAlign.Center,
            lineHeight = 20.sp,
            modifier = Modifier.padding(top = 8.dp, bottom = 12.dp)
        )
    }
}

// =========================================================================
// HALAMAN 3: BANTUAN SATU SENTUHAN (SOS & KEPOLISIAN)
// =========================================================================
@Composable
fun OnboardingPageThree() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Balon Ucapan (Ekor di kiri)
        SpeechBubble(
            text = stringResource(id = R.string.ob3_bubble),
            tailEndPadding = 80.dp,
            isTailOnRight = false
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Area Maskot + 3 Fitur Cepat
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Maskot di Kiri (Ukuran disesuaikan agar proporsional vertikal)
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                MascotIllustration(page = 3, size = 140.dp)
            }

            Spacer(modifier = Modifier.width(10.dp))

            // 3 Badge Fitur di Kanan (Ruang lebih lega dan wrap_content)
            Column(
                modifier = Modifier
                    .weight(1.15f)
                    .wrapContentHeight(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                QuickFeaturePill(
                    icon = Icons.Default.LocationOn,
                    label = stringResource(id = R.string.ob3_f1),
                    modifier = Modifier
                        .fillMaxWidth(0.95f)
                        .align(Alignment.Start)
                )
                QuickFeaturePill(
                    icon = Icons.Default.Shield,
                    label = stringResource(id = R.string.ob3_f2),
                    modifier = Modifier
                        .fillMaxWidth(0.95f)
                        .align(Alignment.End)
                )
                QuickFeaturePill(
                    icon = Icons.Default.NotificationsActive,
                    label = stringResource(id = R.string.ob3_f3),
                    modifier = Modifier
                        .fillMaxWidth(0.95f)
                        .align(Alignment.Start)
                )
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        // Teks Deskripsi
        Text(
            text = stringResource(id = R.string.ob3_tag),
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = Maroon,
            letterSpacing = 1.5.sp
        )

        Text(
            text = stringResource(id = R.string.ob3_title),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Maroon,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 6.dp)
        )

        Text(
            text = stringResource(id = R.string.ob3_body),
            fontSize = 14.sp,
            color = TextDark,
            textAlign = TextAlign.Center,
            lineHeight = 20.sp,
            modifier = Modifier.padding(top = 8.dp, bottom = 12.dp)
        )
    }
}

// =========================================================================
// KOMPONEN REUSABLE
// =========================================================================

/**
 * Balon ucapan (Speech Bubble) dengan ekor penunjuk ke arah maskot.
 */
@Composable
fun SpeechBubble(
    text: String,
    tailEndPadding: androidx.compose.ui.unit.Dp,
    isTailOnRight: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 6.dp)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(elevation = 2.dp, shape = RoundedCornerShape(20.dp)),
            shape = RoundedCornerShape(20.dp),
            color = CardBg,
            border = BorderStroke(1.dp, StrokeColor)
        ) {
            Text(
                text = text,
                color = TextDark,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 18.dp, vertical = 14.dp)
            )
        }

        // Ekor balon ucapan menggunakan Canvas
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp)
        ) {
            Canvas(
                modifier = Modifier
                    .size(24.dp, 10.dp)
                    .then(
                        if (isTailOnRight) {
                            Modifier
                                .align(Alignment.TopEnd)
                                .padding(end = tailEndPadding)
                        } else {
                            Modifier
                                .align(Alignment.TopStart)
                                .padding(start = tailEndPadding)
                        }
                    )
            ) {
                val path = Path().apply {
                    if (isTailOnRight) {
                        moveTo(0f, 0f)
                        lineTo(size.width, 0f)
                        lineTo(size.width * 0.4f, size.height)
                        close()
                    } else {
                        moveTo(0f, 0f)
                        lineTo(size.width, 0f)
                        lineTo(size.width * 0.6f, size.height)
                        close()
                    }
                }
                drawPath(path = path, color = CardBg)
            }
        }
    }
}

/**
 * Maskot interaktif "Si Jaga" menggunakan LottieAnimationView via AndroidView.
 * Kompatibel 100% tanpa dependensi khusus Lottie Compose yang menyebabkan konflik classpath.
 */
@Composable
fun MascotIllustration(
    page: Int,
    size: androidx.compose.ui.unit.Dp = 220.dp
) {
    val rawRes = when (page) {
        1 -> R.raw.mascot_onboarding_1
        2 -> R.raw.mascot_onboarding_2
        else -> R.raw.mascot_onboarding_3
    }

    Box(
        modifier = Modifier.size(size),
        contentAlignment = Alignment.Center
    ) {
        // Blob Latar Belakang Melingkar Lembut (#FBE0DD)
        Box(
            modifier = Modifier
                .size(size * 0.95f)
                .clip(CircleShape)
                .background(Color(0xFFFBE0DD))
        )

        // Animasi Maskot Si Jaga via LottieAnimationView bawaan Lottie Android
        AndroidView(
            factory = { context ->
                LottieAnimationView(context).apply {
                    imageAssetsFolder = "images/"
                    setAnimation(rawRes)
                    repeatCount = LottieDrawable.INFINITE
                    playAnimation()
                }
            },
            update = { view ->
                view.imageAssetsFolder = "images/"
                view.setAnimation(rawRes)
                view.repeatCount = LottieDrawable.INFINITE
                view.playAnimation()
            },
            modifier = Modifier.size(size)
        )
    }
}

/**
 * Kartu fitur sensor kembar (Halaman 2).
 */
@Composable
fun SensorFeatureCard(
    icon: ImageVector,
    title: String,
    desc: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.wrapContentHeight(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardBg),
        border = BorderStroke(1.dp, StrokeColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(vertical = 10.dp, horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .clip(CircleShape)
                    .background(SoftPeach),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = Maroon,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = title,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(3.dp))
            Text(
                text = desc,
                fontSize = 10.sp,
                color = TextGray,
                textAlign = TextAlign.Center,
                lineHeight = 14.sp,
                softWrap = true
            )
        }
    }
}

/**
 * Badge fitur cepat vertikal (Halaman 3).
 */
@Composable
fun QuickFeaturePill(
    icon: ImageVector,
    label: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .wrapContentHeight()
            .shadow(2.dp, RoundedCornerShape(14.dp)),
        shape = RoundedCornerShape(14.dp),
        color = CardBg,
        border = BorderStroke(1.dp, StrokeColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 8.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(SoftPeach),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = label,
                    tint = Maroon,
                    modifier = Modifier.size(16.dp)
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = label,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark,
                textAlign = TextAlign.Center,
                lineHeight = 14.sp,
                softWrap = true
            )
        }
    }
}
