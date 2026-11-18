package com.example.perjalanan

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.perjalanan.ui.screens.auth.LoginScreen
import com.example.perjalanan.ui.theme.PerjalananTheme

class AuthActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PerjalananTheme {
                LoginScreen(
                    onNavigateToHome = {
                        startActivity(Intent(this, HomeActivity::class.java))
                        finish()
                    },
                    onNavigateToPoliceDashboard = {
                        startActivity(Intent(this, PoliceDashboardActivity::class.java))
                        finish()
                    },
                    onNavigateToForgotPassword = {
                        startActivity(Intent(this, VerifyCodeActivity::class.java))
                    },
                    onNavigateToRegister = {
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }
                )
            }
        }
    }
}
