package com.example.perjalanan

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class VerifyCodeActivity : AppCompatActivity() {

    private lateinit var boxes: List<EditText>
    private lateinit var tvResend: TextView
    private var timer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify_code)

        boxes = listOf(R.id.otp1, R.id.otp2, R.id.otp3, R.id.otp4).map { findViewById<EditText>(it) }
        tvResend = findViewById(R.id.tvResend)

        findViewById<android.view.View>(R.id.btnBack).setOnClickListener { finish() }
        setupOtpInputs()
        startTimer()

        findViewById<android.view.View>(R.id.btnVerify).setOnClickListener {
            val code = boxes.joinToString("") { it.text.toString() }
            if (code.length < 4) {
                Toast.makeText(this, "Lengkapi kode verifikasi", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            // TODO: validasi kode ke server
            startActivity(Intent(this, ChangePasswordActivity::class.java))
        }

        tvResend.setOnClickListener {
            if (tvResend.isEnabled) {
                // TODO: kirim ulang kode ke server
                startTimer()
            }
        }
    }

    private fun setupOtpInputs() {
        boxes.forEachIndexed { i, box ->
            box.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    if (s?.length == 1 && i < boxes.lastIndex) boxes[i + 1].requestFocus()
                }
                override fun beforeTextChanged(s: CharSequence?, st: Int, c: Int, a: Int) {}
                override fun onTextChanged(s: CharSequence?, st: Int, b: Int, c: Int) {}
            })
            box.setOnKeyListener { _, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_DEL && event.action == KeyEvent.ACTION_DOWN
                    && box.text.isEmpty() && i > 0) {
                    boxes[i - 1].apply { setText(""); requestFocus() }
                    true
                } else false
            }
        }
        boxes[0].requestFocus()
    }

    private fun startTimer() {
        tvResend.isEnabled = false
        timer?.cancel()
        timer = object : CountDownTimer(59_000, 1_000) {
            override fun onTick(ms: Long) {
                tvResend.text = String.format("Kirim ulang kode (00:%02d)", ms / 1000)
            }
            override fun onFinish() {
                tvResend.text = "Kirim ulang kode"
                tvResend.isEnabled = true
            }
        }.start()
    }

    override fun onDestroy() {
        timer?.cancel()
        super.onDestroy()
    }
}