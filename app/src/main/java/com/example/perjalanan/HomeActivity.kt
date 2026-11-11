package com.example.perjalanan

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        NavHelper.setup(this, findViewById<BottomNavigationView>(R.id.bottomNav), R.id.nav_home)

        // Contoh data; ganti dengan data asli (sensor/API)
        findViewById<TextView>(R.id.tvHeroGreeting)?.text = "Halo, Salsabilaa!"
        findViewById<TextView>(R.id.tvHeartRate).text = "72"

        findViewById<android.view.View>(R.id.btnSos).setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Kirim sinyal SOS?")
                .setMessage("Sinyal darurat beserta lokasi Anda akan dikirim.")
                .setNegativeButton("Batal", null)
                .setPositiveButton("Kirim") { _, _ ->
                    // TODO: kirim SOS ke server / kontak darurat
                    Toast.makeText(this, "Sinyal SOS terkirim", Toast.LENGTH_SHORT).show()
                }
                .show()
        }
    }
}