package com.example.perjalanan

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class PoliceProfileActivity : AppCompatActivity() {

    // Komponen profil
    private lateinit var tvName: TextView
    private lateinit var tvRank: TextView
    private lateinit var tvIdentity: TextView
    private lateinit var tvEmail: TextView
    private lateinit var tvPhone: TextView
    private lateinit var tvUnit: TextView
    private lateinit var tvOffice: TextView

    // Komponen status
    private lateinit var tvOnline: TextView
    private lateinit var tvDutyStatus: TextView
    private lateinit var tvAlarmConnection: TextView
    private lateinit var tvAlarmStatus: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_police_profile)

        initViews()
        loadPoliceData()
        setupClickListeners()
    }

    // ==========================================
    // INISIALISASI KOMPONEN
    // ==========================================

    private fun initViews() {
        tvName = findViewById(R.id.tvPoliceProfileName)
        tvRank = findViewById(R.id.tvPoliceProfileRank)
        tvIdentity = findViewById(R.id.tvPoliceIdentity)
        tvEmail = findViewById(R.id.tvPoliceEmail)
        tvPhone = findViewById(R.id.tvPolicePhone)
        tvUnit = findViewById(R.id.tvPoliceUnit)
        tvOffice = findViewById(R.id.tvPoliceOffice)

        tvOnline = findViewById(R.id.tvPoliceProfileOnline)
        tvDutyStatus = findViewById(R.id.tvPoliceDutyStatus)
        tvAlarmConnection = findViewById(R.id.tvPoliceAlarmConnection)
        tvAlarmStatus = findViewById(R.id.tvPoliceAlarmStatus)
    }

    // ==========================================
    // MEMUAT DATA POLISI
    // ==========================================

    private fun loadPoliceData() {
        tvName.text = "Petugas Unit 07"
        tvRank.text = "PETUGAS KEPOLISIAN"
        tvIdentity.text = "POL-007"
        tvEmail.text = "polisi.unit07@gmail.com"
        tvPhone.text = "081234567890"
        tvUnit.text = "Unit Polisi 07"
        tvOffice.text = "Kantor Polisi Unit 07"

        tvDutyStatus.text = "Siaga"
        tvOnline.text = "● ONLINE"

        tvAlarmConnection.text = "Perangkat terhubung"
        tvAlarmStatus.text = "AKTIF"
    }

    // ==========================================
    // EVENT TOMBOL
    // ==========================================

    private fun setupClickListeners() {
        // KEMBALI
        findViewById<ImageView>(R.id.btnPoliceProfileBack)?.setOnClickListener {
            finish()
        }

        // PENGATURAN UMUM
        findViewById<ImageView>(R.id.btnPoliceProfileSettings)?.setOnClickListener {
            openSettings()
        }

        // PENGATURAN ALARM
        findViewById<LinearLayout>(R.id.btnPoliceAlarmSettings)?.setOnClickListener {
            openAlarmSettings()
        }

        // EDIT PROFIL
        findViewById<TextView>(R.id.btnPoliceEditProfile)?.setOnClickListener {
            openEditProfile()
        }

        // UBAH PASSWORD
        findViewById<TextView>(R.id.btnPoliceChangePassword)?.setOnClickListener {
            openChangePassword()
        }

        // LOGOUT
        findViewById<TextView>(R.id.btnPoliceLogout)?.setOnClickListener {
            showLogoutDialog()
        }
    }

    // ==========================================
    // PENGATURAN
    // ==========================================

    private fun openSettings() {
        startActivity(Intent(this, SettingsActivity::class.java))
    }

    // ==========================================
    // PENGATURAN ALARM
    // ==========================================

    private fun openAlarmSettings() {
        val options = arrayOf(
            "Aktifkan Alarm",
            "Matikan Alarm",
            "Informasi Perangkat Alarm"
        )

        AlertDialog.Builder(this)
            .setTitle("Pengaturan Alarm SOS")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> updateAlarmStatus(true)
                    1 -> updateAlarmStatus(false)
                    2 -> showAlarmInformation()
                }
            }
            .setNegativeButton("Tutup", null)
            .show()
    }

    private fun updateAlarmStatus(enabled: Boolean) {
        tvAlarmStatus.text = if (enabled) "AKTIF" else "NONAKTIF"
        Toast.makeText(
            this,
            if (enabled) "Alarm diaktifkan" else "Alarm dinonaktifkan",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun showAlarmInformation() {
        AlertDialog.Builder(this)
            .setTitle("Informasi Perangkat Alarm")
            .setMessage(
                "Status koneksi: ${tvAlarmConnection.text}\n" +
                        "Status alarm: ${tvAlarmStatus.text}\n\n" +
                        "Perangkat alarm digunakan untuk memberikan peringatan saat laporan SOS masuk."
            )
            .setPositiveButton("OK", null)
            .show()
    }

    // ==========================================
    // EDIT PROFIL
    // ==========================================

    private fun openEditProfile() {
        Toast.makeText(
            this,
            "Fitur edit profil petugas belum dihubungkan",
            Toast.LENGTH_SHORT
        ).show()
    }

    // ==========================================
    // UBAH PASSWORD
    // ==========================================

    private fun openChangePassword() {
        val intent = Intent(this, ChangePasswordActivity::class.java)
        startActivity(intent)
    }

    // ==========================================
    // LOGOUT
    // ==========================================

    private fun showLogoutDialog() {
        AlertDialog.Builder(this)
            .setTitle("Konfirmasi Logout")
            .setMessage("Apakah Anda yakin ingin keluar dari akun polisi?")
            .setNegativeButton("Batal", null)
            .setPositiveButton("Keluar") { _, _ ->
                logoutPolice()
            }
            .show()
    }

    private fun logoutPolice() {
        val intent = Intent(this, AuthActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}
