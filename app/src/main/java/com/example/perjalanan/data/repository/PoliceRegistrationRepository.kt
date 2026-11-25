package com.example.perjalanan.data.repository

import com.example.perjalanan.data.remote.ApiClient
import com.example.perjalanan.data.remote.PoliceApiService
import com.example.perjalanan.data.remote.dto.PoliceRegistrationRequest
import com.example.perjalanan.data.remote.dto.PoliceRequestData
import com.google.gson.Gson
import com.example.perjalanan.data.remote.dto.ErrorResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

interface PoliceRegistrationRepository {
    suspend fun submitRequest(
        polsekName: String,
        fullName: String,
        nrp: String,
        phone: String?,
        reason: String?
    ): Result<String>  // Returns success message

    suspend fun getMyRequest(): Result<PoliceRequestData?>
}

class PoliceRegistrationRepositoryImpl(
    private val apiService: PoliceApiService = ApiClient.policeApiService
) : PoliceRegistrationRepository {

    private val gson = Gson()

    override suspend fun submitRequest(
        polsekName: String,
        fullName: String,
        nrp: String,
        phone: String?,
        reason: String?
    ): Result<String> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.submitPoliceRequest(
                PoliceRegistrationRequest(
                    polsekName = polsekName.trim(),
                    fullName   = fullName.trim(),
                    nrp        = nrp.trim(),
                    phone      = phone?.trim()?.ifBlank { null },
                    reason     = reason?.trim()?.ifBlank { null }
                )
            )
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!.message)
            } else {
                Result.failure(Exception(extractError(response)))
            }
        } catch (e: Exception) {
            Result.failure(Exception(resolveNetworkError(e)))
        }
    }

    override suspend fun getMyRequest(): Result<PoliceRequestData?> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getMyRequest()
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!.request)
            } else {
                Result.failure(Exception(extractError(response)))
            }
        } catch (e: Exception) {
            Result.failure(Exception(resolveNetworkError(e)))
        }
    }

    private fun extractError(response: Response<*>): String {
        return try {
            val json = response.errorBody()?.string()
            if (!json.isNullOrBlank()) {
                gson.fromJson(json, ErrorResponse::class.java).error
                    ?: "Terjadi kesalahan (${response.code()})"
            } else {
                "Terjadi kesalahan (${response.code()})"
            }
        } catch (_: Exception) {
            "Terjadi kesalahan pada server (${response.code()})"
        }
    }

    private fun resolveNetworkError(e: Exception): String {
        val msg = e.message?.lowercase() ?: ""
        return when {
            msg.contains("failed to connect") || msg.contains("connection refused") ->
                "Gagal terhubung ke server. Pastikan server backend berjalan."
            msg.contains("timeout") ->
                "Koneksi ke server timeout. Periksa jaringan Anda."
            else -> e.message ?: "Terjadi kesalahan jaringan"
        }
    }
}
