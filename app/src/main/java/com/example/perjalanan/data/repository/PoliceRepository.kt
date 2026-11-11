package com.example.perjalanan.data.repository

import com.example.perjalanan.data.model.PoliceOfficer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

interface PoliceRepository {
    fun getOfficerProfile(): Flow<PoliceOfficer>
    suspend fun setAlarmActive(isActive: Boolean)
    suspend fun setAlarmVolume(volume: Int)
    suspend fun toggleDutyStatus()
}

class PoliceRepositoryImpl : PoliceRepository {
    private val _officer = MutableStateFlow(PoliceOfficer())

    override fun getOfficerProfile(): Flow<PoliceOfficer> = _officer.asStateFlow()

    override suspend fun setAlarmActive(isActive: Boolean) {
        _officer.update { it.copy(isAlarmActive = isActive) }
    }

    override suspend fun setAlarmVolume(volume: Int) {
        _officer.update { it.copy(alarmVolume = volume) }
    }

    override suspend fun toggleDutyStatus() {
        _officer.update {
            val nextDuty = !it.isDutyActive
            it.copy(
                isDutyActive = nextDuty,
                dutyStatusText = if (nextDuty) "Siaga" else "Istirahat"
            )
        }
    }
}
