package com.example.perjalanan.data.repository

import com.example.perjalanan.data.model.User
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

interface AuthRepository {
    val currentUser: Flow<User?>
    suspend fun login(email: String, password: String): Result<User>
    suspend fun verifyCode(code: String): Result<Boolean>
    suspend fun changePassword(newPass: String): Result<Boolean>
    suspend fun logout()
}

class AuthRepositoryImpl : AuthRepository {
    private val _currentUser = MutableStateFlow<User?>(User())
    override val currentUser: Flow<User?> = _currentUser.asStateFlow()

    override suspend fun login(email: String, password: String): Result<User> {
        delay(600) // Simulate network delay
        if (email.isBlank() || password.isBlank()) {
            return Result.failure(IllegalArgumentException("Email dan kata sandi tidak boleh kosong"))
        }
        val user = User(
            id = "user_01",
            name = if (email.contains("polisi", ignoreCase = true)) "Petugas Unit 07" else "Salsabilaa",
            email = email,
            phone = "081234567890",
            role = if (email.contains("polisi", ignoreCase = true)) "Polisi" else "Pengguna"
        )
        _currentUser.value = user
        return Result.success(user)
    }

    override suspend fun verifyCode(code: String): Result<Boolean> {
        delay(500)
        return if (code.length == 4) {
            Result.success(true)
        } else {
            Result.failure(IllegalArgumentException("Kode verifikasi harus 4 digit"))
        }
    }

    override suspend fun changePassword(newPass: String): Result<Boolean> {
        delay(600)
        return if (newPass.length >= 6) {
            Result.success(true)
        } else {
            Result.failure(IllegalArgumentException("Kata sandi minimal 6 karakter"))
        }
    }

    override suspend fun logout() {
        _currentUser.value = null
    }
}
