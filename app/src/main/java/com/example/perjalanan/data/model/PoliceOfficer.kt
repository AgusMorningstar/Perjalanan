package com.example.perjalanan.data.model

data class PoliceOfficer(
    val id: String = "POL-007",
    val name: String = "Petugas Unit 07",
    val rank: String = "PETUGAS KEPOLISIAN",
    val email: String = "polisi.unit07@gmail.com",
    val phone: String = "081234567890",
    val unit: String = "Unit Polisi 07",
    val office: String = "Kantor Polisi Unit 07",
    val isDutyActive: Boolean = true,
    val dutyStatusText: String = "Siaga",
    val isOnline: Boolean = true,
    val isAlarmConnected: Boolean = true,
    val isAlarmActive: Boolean = true,
    val alarmVolume: Int = 80,
    val activeReportsCount: Int = 3,
    val resolvedReportsCount: Int = 18,
    val availableUnitsCount: Int = 5
)
