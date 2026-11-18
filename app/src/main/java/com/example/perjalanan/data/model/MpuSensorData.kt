package com.example.perjalanan.data.model

data class MpuSensorData(
    val ax: Float = 0f,
    val ay: Float = 0f,
    val az: Float = 1.0f,
    val gx: Float = 0f,
    val gy: Float = 0f,
    val gz: Float = 0f,
    val isMoving: Boolean = false,
    val motionLabel: String = "Diam",
    val rawString: String = ""
)
