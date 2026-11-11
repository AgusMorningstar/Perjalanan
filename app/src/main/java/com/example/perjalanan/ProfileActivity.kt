package com.example.perjalanan

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import com.google.android.material.bottomnavigation.BottomNavigationView

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        NavHelper.setup(this, findViewById<BottomNavigationView>(R.id.bottomNav), R.id.nav_profile)

        val openSettings = { startActivity(Intent(this, SettingsActivity::class.java)) }
        findViewById<android.view.View>(R.id.btnBack).setOnClickListener { finish() }
        findViewById<android.view.View>(R.id.btnSettings).setOnClickListener { openSettings() }
        findViewById<android.view.View>(R.id.rowSettings).setOnClickListener { openSettings() }
        findViewById<android.view.View>(R.id.rowLogout).setOnClickListener {
            LogoutDialog(this).show()
        }
    }
}