package com.example.perjalanan

import android.content.Context
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.perjalanan.data.AppContainer
import com.example.perjalanan.data.model.Language
import com.example.perjalanan.data.model.ThemeMode
import com.example.perjalanan.data.notification.SosNotificationHelper
import com.example.perjalanan.ui.localization.LocalAppStrings
import com.example.perjalanan.ui.localization.getAppStrings
import com.example.perjalanan.ui.navigation.AppNavGraph
import com.example.perjalanan.ui.navigation.Screen
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

            // Update Android Locale Configuration
            val currentContext = LocalContext.current
            val appStrings = getAppStrings(settingsState.language)
            val locale = if (settingsState.language == Language.ENGLISH) Locale.ENGLISH else Locale("in", "ID")
            Locale.setDefault(locale)
            val config = currentContext.resources.configuration
            config.setLocale(locale)
            currentContext.resources.updateConfiguration(config, currentContext.resources.displayMetrics)

            PerjalananTheme(darkTheme = isDarkTheme) {
                CompositionLocalProvider(LocalAppStrings provides appStrings) {
                    key(settingsState.language) {
                        Surface(modifier = Modifier.fillMaxSize()) {
                            AppNavGraph(
                                startDestination = initialDestination ?: Screen.Onboarding.route
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
