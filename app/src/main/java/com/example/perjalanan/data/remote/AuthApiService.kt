package com.example.perjalanan.data.remote

import com.example.perjalanan.data.remote.dto.AuthResponse
import com.example.perjalanan.data.remote.dto.ChangePasswordRequest
import com.example.perjalanan.data.remote.dto.ForgotPasswordRequest
import com.example.perjalanan.data.remote.dto.ForgotPasswordResponse
import com.example.perjalanan.data.remote.dto.GenericResponse
import com.example.perjalanan.data.remote.dto.GoogleLoginRequest
import com.example.perjalanan.data.remote.dto.LoginRequest
import com.example.perjalanan.data.remote.dto.LogoutRequest
import com.example.perjalanan.data.remote.dto.RedeemInviteRequest
import com.example.perjalanan.data.remote.dto.RefreshTokenRequest
import com.example.perjalanan.data.remote.dto.RegisterRequest
import com.example.perjalanan.data.remote.dto.ResetPasswordRequest
import com.example.perjalanan.data.remote.dto.UpdateProfileRequest
import com.example.perjalanan.data.remote.dto.UserResponse
import com.example.perjalanan.data.remote.dto.VerifyOtpRequest
import com.example.perjalanan.data.remote.dto.VerifyOtpResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST

interface AuthApiService {

    @POST("auth/register")
    suspend fun register(
        @Body request: RegisterRequest
    ): Response<AuthResponse>

    @POST("auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<AuthResponse>

    @POST("auth/google")
    suspend fun loginGoogle(
        @Body request: GoogleLoginRequest
    ): Response<AuthResponse>

    @POST("auth/refresh")
    suspend fun refreshToken(
        @Body request: RefreshTokenRequest
    ): Response<AuthResponse>

    @POST("auth/logout")
    suspend fun logout(
        @Body request: LogoutRequest
    ): Response<GenericResponse>

    @POST("auth/redeem-invite")
    suspend fun redeemInvite(
        @Body request: RedeemInviteRequest
    ): Response<UserResponse>

    @GET("me")
    suspend fun getMe(): Response<UserResponse>

    @PATCH("me")
    suspend fun updateProfile(
        @Body request: UpdateProfileRequest
    ): Response<UserResponse>

    @POST("me/password")
    suspend fun changePassword(
        @Body request: ChangePasswordRequest
    ): Response<AuthResponse>

    @POST("auth/forgot-password")
    suspend fun forgotPassword(
        @Body request: ForgotPasswordRequest
    ): Response<ForgotPasswordResponse>

    @POST("auth/verify-otp")
    suspend fun verifyOtp(
        @Body request: VerifyOtpRequest
    ): Response<VerifyOtpResponse>

    @POST("auth/reset-password")
    suspend fun resetPassword(
        @Body request: ResetPasswordRequest
    ): Response<GenericResponse>
}
