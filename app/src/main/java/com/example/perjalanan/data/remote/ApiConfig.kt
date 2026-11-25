package com.example.perjalanan.data.remote

/**
 * Konfigurasi URL Server Backend Perjalanan.
 *
 * - Saat Pengujian HP Fisik (Wi-Fi Lokal):
 *   Gunakan IP LAN laptop Anda (contoh: http://10.227.189.105:3000/).
 *
 * - Saat Deploy ke VPS Kampus:
 *   Cukup ganti BASE_URL ke domain atau IP publik VPS kampus Anda,
 *   misalnya: "https://api.perjalanan.kampus.ac.id/" atau "http://103.x.x.x:3000/".
 */
object ApiConfig {
    const val BASE_URL = "http://10.227.189.105:3000/"
    const val GOOGLE_WEB_CLIENT_ID = "738871871137-gsu880om6t7ng8qtrbh2fpmcrpq7e1t5.apps.googleusercontent.com"
}
