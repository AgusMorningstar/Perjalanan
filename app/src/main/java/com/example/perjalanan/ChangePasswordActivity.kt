package com.example.perjalanan

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class ChangePasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        val etNew = findViewById<TextInputEditText>(R.id.etNewPass)
        val etConfirm = findViewById<TextInputEditText>(R.id.etConfirmPass)

        findViewById<android.view.View>(R.id.btnBack).setOnClickListener { finish() }
        findViewById<android.view.View>(R.id.btnSave).setOnClickListener {
            val newPass = etNew.text.toString()
            val confirm = etConfirm.text.toString()
            when {
                newPass.length < 6 -> etNew.error = "Minimal 6 karakter"
                newPass != confirm -> etConfirm.error = "Kata sandi tidak sama"
                else -> {
                    // TODO: kirim kata sandi baru ke server
                    Toast.makeText(this, "Kata sandi berhasil diubah", Toast.LENGTH_SHORT).show()
                    // Ganti LoginActivity sesuai nama activity login Anda
                    startActivity(
                        Intent(this, AuthActivity::class.java)
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    )
                }
            }
        }
    }
}