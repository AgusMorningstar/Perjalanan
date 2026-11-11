package com.example.perjalanan

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat

class SettingsActivity : AppCompatActivity() {

    private val prefs by lazy { getSharedPreferences("settings", MODE_PRIVATE) }
    private lateinit var tvTheme: TextView
    private lateinit var tvLanguage: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        tvTheme = findViewById(R.id.tvTheme)
        tvLanguage = findViewById(R.id.tvLanguage)
        findViewById<android.view.View>(R.id.btnBack).setOnClickListener { finish() }

        refreshLabels()
        findViewById<android.view.View>(R.id.rowLanguage).setOnClickListener { chooseLanguage() }
        findViewById<android.view.View>(R.id.rowTheme).setOnClickListener { chooseTheme() }
    }

    private fun refreshLabels() {
        tvTheme.text = when (prefs.getInt("night_mode", AppCompatDelegate.MODE_NIGHT_NO)) {
            AppCompatDelegate.MODE_NIGHT_YES -> "Gelap"
            AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM -> "Sistem"
            else -> "Terang"
        }
        tvLanguage.text = if (prefs.getString("lang", "id") == "en") "English" else "Indonesia"
    }

    private fun chooseTheme() {
        val labels = arrayOf("Terang", "Gelap", "Ikuti sistem")
        val modes = intArrayOf(
            AppCompatDelegate.MODE_NIGHT_NO,
            AppCompatDelegate.MODE_NIGHT_YES,
            AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        )
        AlertDialog.Builder(this)
            .setTitle("Mode Tampilan")
            .setItems(labels) { _, which ->
                prefs.edit().putInt("night_mode", modes[which]).apply()
                AppCompatDelegate.setDefaultNightMode(modes[which])
                refreshLabels()
            }.show()
    }

    private fun chooseLanguage() {
        val labels = arrayOf("Indonesia", "English")
        val tags = arrayOf("id", "en")
        AlertDialog.Builder(this)
            .setTitle("Ubah Bahasa")
            .setItems(labels) { _, which ->
                prefs.edit().putString("lang", tags[which]).apply()
                AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(tags[which]))
                refreshLabels()
            }.show()
    }
}