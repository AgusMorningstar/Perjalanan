package com.example.perjalanan

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        findViewById<MaterialButton>(R.id.signInButton)?.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        findViewById<TextView>(R.id.forgotPasswordLink)?.setOnClickListener {
            val intent = Intent(this, VerifyCodeActivity::class.java)
            startActivity(intent)
        }
    }
}
