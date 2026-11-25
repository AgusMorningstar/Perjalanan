package com.example.perjalanan

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.perjalanan.data.AppContainer
import com.example.perjalanan.data.model.Language
import com.example.perjalanan.data.model.ThemeMode
import com.example.perjalanan.data.notification.SosNotificationHelper
import com.example.perjalanan.ui.localization.LocalAppStrings
import com.example.perjalanan.ui.localization.getAppStrings
import com.example.perjalanan.ui.navigation.AppNavGraph
import com.example.perjalanan.ui.navigation.Screen
import com.example.perjalanan.ui.theme.DarkAppColors
import com.example.perjalanan.ui.theme.LightAppColors
import com.example.perjalanan.ui.theme.LocalAppColors
import com.example.perjalanan.ui.theme.PerjalananTheme
import java.util.Locale

class MainActivity : ComponentActivity() {

    private var initialDestination by mutableStateOf<String?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // 1. Buat Notification Channel untuk pop-up SOS
        SosNotificationHelper.createNotificationChannel(this)

        // 2. Minta izin Notifikasi (Android 13+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 1001)
            }
        }

        // 3. Periksa apakah dibuka dari klik notifikasi "Terima & Lihat Lokasi"
        handleNotificationIntent(intent)

        setContent {
            val settingsState by AppContainer.settingsRepository.getSettings().collectAsState(initial = com.example.perjalanan.data.model.AppSettings())
            val systemDark = isSystemInDarkTheme()
            val isDarkTheme = when (settingsState.themeMode) {
                ThemeMode.LIGHT -> false
                ThemeMode.DARK -> true
                ThemeMode.SYSTEM -> systemDark
            }

            // Update Android Locale Configuration safely in LaunchedEffect
            val appStrings = getAppStrings(settingsState.language)
            LaunchedEffect(settingsState.language) {
                val locale = if (settingsState.language == Language.ENGLISH) Locale.ENGLISH else Locale("in", "ID")
                Locale.setDefault(locale)
                @Suppress("DEPRECATION")
                val config = this@MainActivity.resources.configuration
                config.setLocale(locale)
                @Suppress("DEPRECATION")
                this@MainActivity.resources.updateConfiguration(config, this@MainActivity.resources.displayMetrics)
            }

            // Tentukan layar awal secara dinamis: jika sudah login langsung ke Home/PoliceDashboard
            val authRepo = AppContainer.authRepository
            val defaultStartRoute = if (authRepo.isLoggedIn()) {
                val user = authRepo.getSavedUser()
                if (user?.isPolice == true) Screen.PoliceDashboard.route else Screen.Home.route
            } else {
                Screen.Onboarding.route
            }

            PerjalananTheme(darkTheme = isDarkTheme) {
                val appColors = if (isDarkTheme) DarkAppColors else LightAppColors
                CompositionLocalProvider(
                    LocalAppStrings provides appStrings,
                    LocalAppColors provides appColors
                ) {
                    key(settingsState.language, isDarkTheme) {
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = appColors.screenBackground
                        ) {
                            AppNavGraph(
                                startDestination = initialDestination ?: defaultStartRoute
                            )
                        }
                    }
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleNotificationIntent(intent)
    }

    private fun handleNotificationIntent(intent: Intent?) {
        val target = intent?.getStringExtra(SosNotificationHelper.EXTRA_NAVIGATE_TO)
        if (!target.isNullOrBlank()) {
            initialDestination = target
        }
    }
}
