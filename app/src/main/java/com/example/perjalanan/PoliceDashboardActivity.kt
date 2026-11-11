package com.example.perjalanan

import android.os.Bundle
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class PoliceDashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_police_dashboard)

        val seekVolume = findViewById<SeekBar?>(R.id.seekAlarmVolume)
        val tvVolume = findViewById<TextView?>(R.id.tvAlarmVolume)
        val btnStopAlarm = findViewById<MaterialButton?>(R.id.btnStopAlarm)
        val btnAcceptReport = findViewById<MaterialButton?>(R.id.btnAcceptReport)

        seekVolume?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                tvVolume?.text = "$progress%"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        btnStopAlarm?.setOnClickListener {
            Toast.makeText(this, "Alarm dimatikan", Toast.LENGTH_SHORT).show()
        }

        btnAcceptReport?.setOnClickListener {
            Toast.makeText(this, "Laporan diterima", Toast.LENGTH_SHORT).show()
        }
    }
}
