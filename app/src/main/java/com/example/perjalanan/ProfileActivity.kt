package com.example.perjalanan

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.perjalanan.ui.navigation.Screen
import com.example.perjalanan.ui.screens.profile.ProfileScreen
import com.example.perjalanan.ui.theme.PerjalananTheme

class ProfileActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PerjalananTheme {
                ProfileScreen(
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
                            Screen.Emergency -> {
                                startActivity(Intent(this, EmergencyActivity::class.java))
                                finish()
                            }
                            Screen.Settings -> {
                                startActivity(Intent(this, SettingsActivity::class.java))
                            }
                            else -> Unit
                        }
                    },
                    onLogoutSuccess = {
                        val intent = Intent(this, AuthActivity::class.java).apply {
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        }
                        startActivity(intent)
                        finish()
                    }
                )
            }
        }
    }
}
