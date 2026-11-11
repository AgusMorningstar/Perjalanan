package com.example.perjalanan

import android.content.Context
import android.content.Intent

/**
 * Legacy LogoutDialog helper.
 * Di Jetpack Compose, gunakan Composable [com.example.perjalanan.ui.components.LogoutConfirmDialog].
 */
class LogoutDialog(private val context: Context) {
    fun show() {
        context.startActivity(
            Intent(context, AuthActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        )
    }
}
