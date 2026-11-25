package com.example.perjalanan.data.repository

import com.example.perjalanan.data.local.SessionManager
import com.example.perjalanan.data.model.User
import com.example.perjalanan.data.remote.ApiClient
import com.example.perjalanan.data.remote.AuthApiService
import com.example.perjalanan.data.remote.dto.ChangePasswordRequest
import com.example.perjalanan.data.remote.dto.ErrorResponse
import com.example.perjalanan.data.remote.dto.ForgotPasswordRequest
import com.example.perjalanan.data.remote.dto.ForgotPasswordResponse
import com.example.perjalanan.data.remote.dto.GoogleLoginRequest
import com.example.perjalanan.data.remote.dto.LoginRequest
import com.example.perjalanan.data.remote.dto.LogoutRequest
import com.example.perjalanan.data.remote.dto.RedeemInviteRequest
import com.example.perjalanan.data.remote.dto.RegisterRequest
import com.example.perjalanan.data.remote.dto.ResetPasswordRequest
import com.example.perjalanan.data.remote.dto.UpdateProfileRequest
import com.example.perjalanan.data.remote.dto.VerifyOtpRequest
import com.example.perjalanan.data.remote.dto.VerifyOtpResponse
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import retrofit2.Response

interface AuthRepository {
    val currentUser: Flow<User?>
    fun isLoggedIn(): Boolean
    fun getSavedUser(): User?
    suspend fun login(email: String, password: String): Result<User>
    suspend fun register(username: String, email: String, password: String): Result<User>
    suspend fun loginWithGoogle(idToken: String): Result<User>
    suspend fun redeemInvite(code: String): Result<User>
    suspend fun requestForgotPasswordOtp(identifier: String): Result<ForgotPasswordResponse>
    suspend fun verifyOtp(identifier: String, otp: String): Result<String>
    suspend fun resetPassword(resetToken: String, newPass: String): Result<Boolean>
    suspend fun verifyCode(code: String): Result<Boolean>
    suspend fun changePassword(newPass: String): Result<Boolean>
    suspend fun updateProfile(
        username: String? = null,
        email: String? = null,
        phone: String? = null,
        currentPassword: String
    ): Result<User>
    suspend fun changePasswordWithOld(
        currentPassword: String,
        newPassword: String
    ): Result<Boolean>
    suspend fun logout()
}

class AuthRepositoryImpl(
    private val apiService: AuthApiService = ApiClient.authApiService,
    private val sessionManager: SessionManager = ApiClient.sessionManager
) : AuthRepository {

    private val gson = Gson()

    override val currentUser: Flow<User?> = sessionManager.currentUserFlow

    override fun isLoggedIn(): Boolean = sessionManager.isLoggedIn()

    override fun getSavedUser(): User? = sessionManager.getUser()

    override suspend fun login(email: String, password: String): Result<User> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.login(LoginRequest(email.trim(), password))
            if (response.isSuccessful && response.body() != null) {
                val authResult = response.body()!!
                sessionManager.saveSession(
                    authResult.user,
                    authResult.accessToken,
                    authResult.refreshToken
                )
                val loggedInUser = sessionManager.getUser() ?: User(
                    id = authResult.user.id,
                    name = authResult.user.username,
                    email = authResult.user.email,
                    role = authResult.user.role,
                    polsekId = authResult.user.polsekId,
                    polsekName = authResult.user.polsekName
                )
                Result.success(loggedInUser)
            } else {
                val errorMsg = extractErrorMessage(response)
                Result.failure(Exception(errorMsg))
            }
        } catch (e: Exception) {
            Result.failure(Exception(resolveNetworkException(e)))
        }
    }

    override suspend fun register(
        username: String,
        email: String,
        password: String
    ): Result<User> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.register(
                RegisterRequest(
                    username = username.trim(),
                    email = email.trim().lowercase(),
                    password = password
                )
            )
            if (response.isSuccessful && response.body() != null) {
                val authResult = response.body()!!
                sessionManager.saveSession(
                    authResult.user,
                    authResult.accessToken,
                    authResult.refreshToken
                )
                val registeredUser = sessionManager.getUser() ?: User(
                    id = authResult.user.id,
                    name = authResult.user.username,
                    email = authResult.user.email,
                    role = authResult.user.role,
                    polsekId = authResult.user.polsekId,
                    polsekName = authResult.user.polsekName
                )
                Result.success(registeredUser)
            } else {
                val errorMsg = extractErrorMessage(response)
                Result.failure(Exception(errorMsg))
            }
        } catch (e: Exception) {
            Result.failure(Exception(resolveNetworkException(e)))
        }
    }

    override suspend fun loginWithGoogle(idToken: String): Result<User> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.loginGoogle(GoogleLoginRequest(idToken))
            if (response.isSuccessful && response.body() != null) {
                val authResult = response.body()!!
                sessionManager.saveSession(
                    authResult.user,
                    authResult.accessToken,
                    authResult.refreshToken
                )
                val user = sessionManager.getUser() ?: User(
                    id = authResult.user.id,
                    name = authResult.user.username,
                    email = authResult.user.email,
                    role = authResult.user.role,
                    polsekId = authResult.user.polsekId,
                    polsekName = authResult.user.polsekName
                )
                Result.success(user)
            } else {
                val errorMsg = extractErrorMessage(response)
                Result.failure(Exception(errorMsg))
            }
        } catch (e: Exception) {
            Result.failure(Exception(resolveNetworkException(e)))
        }
    }

    override suspend fun redeemInvite(code: String): Result<User> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.redeemInvite(RedeemInviteRequest(code.trim().uppercase()))
            if (response.isSuccessful && response.body() != null) {
                val userDto = response.body()!!.user
                sessionManager.updateUser(userDto)
                val updatedUser = sessionManager.getUser()!!
                Result.success(updatedUser)
            } else {
                val errorMsg = extractErrorMessage(response)
                Result.failure(Exception(errorMsg))
            }
        } catch (e: Exception) {
            Result.failure(Exception(resolveNetworkException(e)))
        }
    }

    override suspend fun requestForgotPasswordOtp(identifier: String): Result<ForgotPasswordResponse> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.forgotPassword(ForgotPasswordRequest(identifier.trim()))
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                val errorMsg = extractErrorMessage(response)
                Result.failure(Exception(errorMsg))
            }
        } catch (e: Exception) {
            Result.failure(Exception(resolveNetworkException(e)))
        }
    }

    override suspend fun verifyOtp(identifier: String, otp: String): Result<String> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.verifyOtp(VerifyOtpRequest(identifier.trim(), otp.trim()))
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!.resetToken)
            } else {
                val errorMsg = extractErrorMessage(response)
                Result.failure(Exception(errorMsg))
            }
        } catch (e: Exception) {
            Result.failure(Exception(resolveNetworkException(e)))
        }
    }

    override suspend fun resetPassword(resetToken: String, newPass: String): Result<Boolean> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.resetPassword(ResetPasswordRequest(resetToken.trim(), newPass))
            if (response.isSuccessful) {
                Result.success(true)
            } else {
                val errorMsg = extractErrorMessage(response)
                Result.failure(Exception(errorMsg))
            }
        } catch (e: Exception) {
            Result.failure(Exception(resolveNetworkException(e)))
        }
    }

    override suspend fun verifyCode(code: String): Result<Boolean> {
        return if (code.length == 4) {
            Result.success(true)
        } else {
            Result.failure(IllegalArgumentException("Kode verifikasi harus 4 digit"))
        }
    }

    override suspend fun changePassword(newPass: String): Result<Boolean> {
        return if (newPass.length >= 8) {
            Result.success(true)
        } else {
            Result.failure(IllegalArgumentException("Kata sandi minimal 8 karakter"))
        }
    }

    override suspend fun updateProfile(
        username: String?,
        email: String?,
        phone: String?,
        currentPassword: String
    ): Result<User> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.updateProfile(
                UpdateProfileRequest(
                    username = username,
                    email = email,
                    phone = phone,
                    currentPassword = currentPassword
                )
            )
            if (response.isSuccessful && response.body() != null) {
                val userDto = response.body()!!.user
                sessionManager.updateUser(userDto)
                val updatedUser = sessionManager.getUser()!!
                Result.success(updatedUser)
            } else {
                val errorMsg = extractErrorMessage(response)
                Result.failure(Exception(errorMsg))
            }
        } catch (e: Exception) {
            Result.failure(Exception(resolveNetworkException(e)))
        }
    }

    override suspend fun changePasswordWithOld(
        currentPassword: String,
        newPassword: String
    ): Result<Boolean> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.changePassword(
                ChangePasswordRequest(
                    currentPassword = currentPassword,
                    newPassword = newPassword
                )
            )
            if (response.isSuccessful && response.body() != null) {
                val authResult = response.body()!!
                sessionManager.saveSession(
                    authResult.user,
                    authResult.accessToken,
                    authResult.refreshToken
                )
                Result.success(true)
            } else {
                val errorMsg = extractErrorMessage(response)
                Result.failure(Exception(errorMsg))
            }
        } catch (e: Exception) {
            Result.failure(Exception(resolveNetworkException(e)))
        }
    }

    override suspend fun logout() = withContext(Dispatchers.IO) {
        try {
            val refreshToken = sessionManager.getRefreshToken()
            if (!refreshToken.isNullOrBlank()) {
                apiService.logout(LogoutRequest(refreshToken))
            }
        } catch (_: Exception) {
            // Abaikan kesalahan jaringan saat logout agar sesi lokal tetap dibersihkan
        } finally {
            sessionManager.clearSession()
        }
    }

    private fun extractErrorMessage(response: Response<*>): String {
        return try {
            val errorJson = response.errorBody()?.string()
            if (!errorJson.isNullOrBlank()) {
                val errorResponse = gson.fromJson(errorJson, ErrorResponse::class.java)
                errorResponse.error ?: "Terjadi kesalahan (Kode: ${response.code()})"
            } else {
                "Terjadi kesalahan (Kode: ${response.code()})"
            }
        } catch (_: Exception) {
            "Terjadi kesalahan pada server (Kode: ${response.code()})"
        }
    }

    private fun resolveNetworkException(e: Exception): String {
        val msg = e.message?.lowercase() ?: ""
        return when {
            msg.contains("failed to connect") || msg.contains("connection refused") ->
                "Gagal terhubung ke server backend (IP: ${com.example.perjalanan.data.remote.ApiConfig.BASE_URL}). Pastikan server backend berjalan di laptop dan HP dalam satu jaringan Wi-Fi."
            msg.contains("timeout") ->
                "Koneksi ke server waktu habis (timeout). Periksa firewall atau koneksi jaringan Anda."
            else -> e.message ?: "Terjadi kesalahan jaringan"
        }
    }
}
