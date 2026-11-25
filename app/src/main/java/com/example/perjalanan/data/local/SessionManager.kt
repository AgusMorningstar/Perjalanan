package com.example.perjalanan.data.local

import android.content.Context
import android.content.SharedPreferences
import com.example.perjalanan.App
import com.example.perjalanan.data.model.User
import com.example.perjalanan.data.remote.dto.UserDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SessionManager(context: Context = App.instance.applicationContext) {

    private val prefs: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    private val _currentUserFlow = MutableStateFlow<User?>(getUser())
    val currentUserFlow: StateFlow<User?> = _currentUserFlow.asStateFlow()

    companion object {
        private const val PREF_NAME = "perjalanan_auth_session"
        private const val KEY_ACCESS_TOKEN = "key_access_token"
        private const val KEY_REFRESH_TOKEN = "key_refresh_token"
        private const val KEY_USER_ID = "key_user_id"
        private const val KEY_USERNAME = "key_username"
        private const val KEY_EMAIL = "key_email"
        private const val KEY_PHONE = "key_phone"
        private const val KEY_ROLE = "key_role"
        private const val KEY_POLSEK_ID = "key_polsek_id"
        private const val KEY_POLSEK_NAME = "key_polsek_name"
    }

    fun saveSession(user: UserDto, accessToken: String, refreshToken: String) {
        prefs.edit()
            .putString(KEY_ACCESS_TOKEN, accessToken)
            .putString(KEY_REFRESH_TOKEN, refreshToken)
            .putString(KEY_USER_ID, user.id)
            .putString(KEY_USERNAME, user.username)
            .putString(KEY_EMAIL, user.email)
            .putString(KEY_PHONE, user.phone ?: "")
            .putString(KEY_ROLE, user.role)
            .putString(KEY_POLSEK_ID, user.polsekId)
            .putString(KEY_POLSEK_NAME, user.polsekName)
            .apply()

        _currentUserFlow.value = mapToUser(user)
    }

    fun updateTokens(accessToken: String, refreshToken: String) {
        prefs.edit()
            .putString(KEY_ACCESS_TOKEN, accessToken)
            .putString(KEY_REFRESH_TOKEN, refreshToken)
            .apply()
    }

    fun updateUser(user: UserDto) {
        prefs.edit()
            .putString(KEY_USER_ID, user.id)
            .putString(KEY_USERNAME, user.username)
            .putString(KEY_EMAIL, user.email)
            .putString(KEY_PHONE, user.phone ?: "")
            .putString(KEY_ROLE, user.role)
            .putString(KEY_POLSEK_ID, user.polsekId)
            .putString(KEY_POLSEK_NAME, user.polsekName)
            .apply()

        _currentUserFlow.value = mapToUser(user)
    }

    fun getAccessToken(): String? = prefs.getString(KEY_ACCESS_TOKEN, null)

    fun getRefreshToken(): String? = prefs.getString(KEY_REFRESH_TOKEN, null)

    fun getUser(): User? {
        val id = prefs.getString(KEY_USER_ID, null) ?: return null
        return User(
            id = id,
            name = prefs.getString(KEY_USERNAME, "") ?: "",
            email = prefs.getString(KEY_EMAIL, "") ?: "",
            phone = prefs.getString(KEY_PHONE, "") ?: "",
            role = prefs.getString(KEY_ROLE, "user") ?: "user",
            polsekId = prefs.getString(KEY_POLSEK_ID, null),
            polsekName = prefs.getString(KEY_POLSEK_NAME, null)
        )
    }

    fun isLoggedIn(): Boolean {
        return !getAccessToken().isNullOrBlank() && getUser() != null
    }

    fun clearSession() {
        prefs.edit().clear().apply()
        _currentUserFlow.value = null
    }

    private fun mapToUser(dto: UserDto): User {
        return User(
            id = dto.id,
            name = dto.username,
            email = dto.email,
            phone = dto.phone ?: "",
            role = dto.role,
            polsekId = dto.polsekId,
            polsekName = dto.polsekName
        )
    }
}
