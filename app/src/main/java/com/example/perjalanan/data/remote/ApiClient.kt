package com.example.perjalanan.data.remote

import android.util.Log
import com.example.perjalanan.data.local.SessionManager
import com.example.perjalanan.data.remote.dto.AuthResponse
import com.example.perjalanan.data.remote.dto.RefreshTokenRequest
import com.google.gson.Gson
import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okhttp3.Route
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {

    private const val TAG = "ApiClient"
    val sessionManager: SessionManager by lazy { SessionManager() }
    private val gson = Gson()

    private val authInterceptor = Interceptor { chain ->
        val originalRequest = chain.request()
        val token = sessionManager.getAccessToken()

        val requestBuilder = originalRequest.newBuilder()
        if (!token.isNullOrBlank() && originalRequest.header("Authorization") == null) {
            requestBuilder.addHeader("Authorization", "Bearer $token")
        }

        chain.proceed(requestBuilder.build())
    }

    private val tokenAuthenticator = object : Authenticator {
        override fun authenticate(route: Route?, response: Response): Request? {
            // Jika request yang gagal adalah request refresh itu sendiri, hentikan perulangan
            if (response.request.url.encodedPath.contains("auth/refresh") ||
                response.request.url.encodedPath.contains("auth/login") ||
                response.request.url.encodedPath.contains("auth/register")
            ) {
                return null
            }

            // Hitung percobaan authenticate untuk mencegah infinite loop
            if (responseCount(response) >= 3) {
                sessionManager.clearSession()
                return null
            }

            val currentRefreshToken = sessionManager.getRefreshToken() ?: run {
                sessionManager.clearSession()
                return null
            }

            // Lakukan panggilan refresh token secara sinkron
            synchronized(this) {
                // Periksa apakah token sudah diperbarui oleh thread lain
                val latestAccessToken = sessionManager.getAccessToken()
                val requestToken = response.request.header("Authorization")?.replace("Bearer ", "")
                if (!latestAccessToken.isNullOrBlank() && latestAccessToken != requestToken) {
                    return response.request.newBuilder()
                        .header("Authorization", "Bearer $latestAccessToken")
                        .build()
                }

                val refreshPayload = gson.toJson(RefreshTokenRequest(currentRefreshToken))
                val refreshRequest = Request.Builder()
                    .url(ApiConfig.BASE_URL + "auth/refresh")
                    .post(refreshPayload.toRequestBody("application/json".toMediaType()))
                    .build()

                try {
                    val rawClient = OkHttpClient.Builder()
                        .connectTimeout(15, TimeUnit.SECONDS)
                        .readTimeout(15, TimeUnit.SECONDS)
                        .build()

                    val refreshResponse = rawClient.newCall(refreshRequest).execute()
                    if (refreshResponse.isSuccessful) {
                        val bodyString = refreshResponse.body?.string()
                        val authResult = gson.fromJson(bodyString, AuthResponse::class.java)
                        if (authResult != null) {
                            sessionManager.saveSession(
                                authResult.user,
                                authResult.accessToken,
                                authResult.refreshToken
                            )
                            return response.request.newBuilder()
                                .header("Authorization", "Bearer ${authResult.accessToken}")
                                .build()
                        }
                    } else {
                        Log.w(TAG, "Refresh token expired or revoked. Status: ${refreshResponse.code}")
                        sessionManager.clearSession()
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "Gagal refresh token: ${e.message}", e)
                }
            }

            return null
        }

        private fun responseCount(response: Response): Int {
            var count = 1
            var prior = response.priorResponse
            while (prior != null) {
                count++
                prior = prior.priorResponse
            }
            return count
        }
    }

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .writeTimeout(15, TimeUnit.SECONDS)
        .addInterceptor(authInterceptor)
        .authenticator(tokenAuthenticator)
        .addInterceptor(loggingInterceptor)
        .build()

    val authApiService: AuthApiService by lazy {
        Retrofit.Builder()
            .baseUrl(ApiConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthApiService::class.java)
    }

    val policeApiService: PoliceApiService by lazy {
        Retrofit.Builder()
            .baseUrl(ApiConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PoliceApiService::class.java)
    }
}
