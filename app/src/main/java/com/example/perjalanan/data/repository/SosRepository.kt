package com.example.perjalanan.data.repository

import com.example.perjalanan.data.model.SosReport
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

interface SosRepository {
    fun getSosReports(): Flow<List<SosReport>>
    suspend fun triggerSos(victimName: String, lat: Double, lng: Double, address: String): Result<SosReport>
    suspend fun acceptReport(reportId: String): Result<Boolean>
}

class SosRepositoryImpl : SosRepository {
    private val _reports = MutableStateFlow(
        listOf(
            SosReport(
                id = "SOS-001",
                victimName = "Salsabilaa",
                victimPhone = "081234567890",
                latitude = -5.1477,
                longitude = 119.4327,
                locationAddress = "Jl. Urip Sumoharjo, Makassar",
                heartRate = 125,
                timestamp = "14:32 WITA",
                isEmergency = true,
                isAccepted = false
            ),
            SosReport(
                id = "SOS-002",
                victimName = "Ahmad Pratama",
                victimPhone = "082198765432",
                latitude = -5.1350,
                longitude = 119.4230,
                locationAddress = "Pantai Losari, Makassar",
                heartRate = 110,
                timestamp = "13:15 WITA",
                isEmergency = true,
                isAccepted = true
            )
        )
    )

    override fun getSosReports(): Flow<List<SosReport>> = _reports.asStateFlow()

    override suspend fun triggerSos(
        victimName: String,
        lat: Double,
        lng: Double,
        address: String
    ): Result<SosReport> {
        delay(500)
        val timeNow = SimpleDateFormat("HH:mm 'WITA'", Locale.getDefault()).format(Date())
        val newReport = SosReport(
            id = "SOS-00${_reports.value.size + 1}",
            victimName = victimName,
            victimPhone = "081234567890",
            latitude = lat,
            longitude = lng,
            locationAddress = address,
            heartRate = 135,
            timestamp = timeNow,
            isEmergency = true,
            isAccepted = false
        )
        _reports.update { listOf(newReport) + it }
        return Result.success(newReport)
    }

    override suspend fun acceptReport(reportId: String): Result<Boolean> {
        delay(400)
        _reports.update { list ->
            list.map { if (it.id == reportId) it.copy(isAccepted = true) else it }
        }
        return Result.success(true)
    }
}
