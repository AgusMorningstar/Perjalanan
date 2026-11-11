package com.example.perjalanan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.perjalanan.ui.screens.settings.SettingsScreen
import com.example.perjalanan.ui.theme.PerjalananTheme

class SettingsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PerjalananTheme {
                SettingsScreen(
                    onNavigateBack = { finish() }
                )
            }
        }
    }
}
