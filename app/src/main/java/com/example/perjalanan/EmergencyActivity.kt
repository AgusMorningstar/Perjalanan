package com.example.perjalanan

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.perjalanan.ui.navigation.Screen
import com.example.perjalanan.ui.screens.emergency.EmergencyScreen
import com.example.perjalanan.ui.theme.PerjalananTheme

class EmergencyActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PerjalananTheme {
                EmergencyScreen(
                    onNavigate = { screen ->
                        when (screen) {
                            Screen.Home -> {
                                startActivity(Intent(this, HomeActivity::class.java))
                                finish()
                            }
                            Screen.Map -> {
                                startActivity(Intent(this, MapActivity::class.java))
                                finish()
                            }
                            Screen.Profile -> {
                                startActivity(Intent(this, ProfileActivity::class.java))
                                finish()
                            }
                            else -> Unit
                        }
                    }
                )
            }
        }
    }
}
