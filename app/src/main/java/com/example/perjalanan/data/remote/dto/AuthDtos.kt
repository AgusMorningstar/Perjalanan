package com.example.perjalanan.data.remote.dto

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    val username: String,
    val email: String,
    val password: String
)

data class LoginRequest(
    val email: String,
    val password: String
)

data class GoogleLoginRequest(
    val idToken: String
)

data class RefreshTokenRequest(
    val refreshToken: String
)

data class LogoutRequest(
    val refreshToken: String
)

data class RedeemInviteRequest(
    val code: String
)

data class UserDto(
    val id: String,
    val username: String,
    val email: String,
    val phone: String? = null,
    val role: String, // "user" atau "police"
    val polsekId: String? = null,
    val polsekName: String? = null,
    val hasPassword: Boolean = true
)

data class UpdateProfileRequest(
    val username: String? = null,
    val email: String? = null,
    val phone: String? = null,
    val currentPassword: String
)

data class ChangePasswordRequest(
    val currentPassword: String,
    val newPassword: String
)

data class ForgotPasswordRequest(
    val identifier: String
)

data class ForgotPasswordResponse(
    val message: String,
    val channel: String,
    val destination: String
)

data class VerifyOtpRequest(
    val identifier: String,
    val otp: String
)

data class VerifyOtpResponse(
    val message: String,
    val resetToken: String
)

data class ResetPasswordRequest(
    val resetToken: String,
    val newPassword: String
)

data class AuthResponse(
    val user: UserDto,
    val accessToken: String,
    val refreshToken: String
)

data class UserResponse(
    val user: UserDto
)

data class GenericResponse(
    val ok: Boolean? = null,
    val message: String? = null
)

data class ErrorResponse(
    val error: String? = null
)
