package com.example.perjalanan.data.model

data class SosReport(
    val id: String,
    val victimName: String,
    val victimPhone: String,
    val latitude: Double,
    val longitude: Double,
    val locationAddress: String,
    val heartRate: Int,
    val timestamp: String,
    val isEmergency: Boolean = true,
    val isAccepted: Boolean = false
)
