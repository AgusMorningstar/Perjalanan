package com.example.perjalanan

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.perjalanan.ui.screens.auth.VerifyCodeScreen
import com.example.perjalanan.ui.theme.PerjalananTheme

class VerifyCodeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PerjalananTheme {
                VerifyCodeScreen(
                    onNavigateBack = { finish() },
                    onNavigateToChangePassword = {
                        startActivity(Intent(this, ChangePasswordActivity::class.java))
                        finish()
                    }
                )
            }
        }
    }
}
