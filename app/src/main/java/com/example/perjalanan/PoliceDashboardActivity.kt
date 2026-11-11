package com.example.perjalanan

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.perjalanan.ui.screens.police.PoliceDashboardScreen
import com.example.perjalanan.ui.theme.PerjalananTheme

class PoliceDashboardActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PerjalananTheme {
                PoliceDashboardScreen(
                    onNavigateToPoliceProfile = {
                        startActivity(Intent(this, PoliceProfileActivity::class.java))
                    },
                    onNavigateBack = { finish() }
                )
            }
        }
    }
}
