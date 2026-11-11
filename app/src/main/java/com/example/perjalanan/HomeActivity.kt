package com.example.perjalanan

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.perjalanan.ui.navigation.Screen
import com.example.perjalanan.ui.screens.home.HomeScreen
import com.example.perjalanan.ui.theme.PerjalananTheme

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PerjalananTheme {
                HomeScreen(
                    onNavigate = { screen ->
                        when (screen) {
                            Screen.Map -> startActivity(Intent(this, MapActivity::class.java))
                            Screen.Profile -> startActivity(Intent(this, ProfileActivity::class.java))
                            Screen.Emergency -> startActivity(Intent(this, EmergencyActivity::class.java))
                            Screen.PoliceDashboard -> startActivity(Intent(this, PoliceDashboardActivity::class.java))
                            else -> Unit
                        }
                    }
                )
            }
        }
    }
}
