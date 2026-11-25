package com.example.perjalanan.data.model

data class User(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val role: String = "user", // "user" atau "police"
    val polsekId: String? = null,
    val polsekName: String? = null,
    val avatarUrl: String? = null
) {
    val isPolice: Boolean
        get() = role.equals("police", ignoreCase = true) || role.equals("polisi", ignoreCase = true)

    val displayRole: String
        get() = if (isPolice) "Polisi" else "Pengguna"
}
