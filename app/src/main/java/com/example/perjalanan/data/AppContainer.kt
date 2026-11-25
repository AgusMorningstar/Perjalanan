package com.example.perjalanan.data

import com.example.perjalanan.data.repository.AuthRepository
import com.example.perjalanan.data.repository.AuthRepositoryImpl
import com.example.perjalanan.data.repository.HealthRepository
import com.example.perjalanan.data.repository.HealthRepositoryImpl
import com.example.perjalanan.data.repository.PoliceRegistrationRepository
import com.example.perjalanan.data.repository.PoliceRegistrationRepositoryImpl
import com.example.perjalanan.data.repository.PoliceRepository
import com.example.perjalanan.data.repository.PoliceRepositoryImpl
import com.example.perjalanan.data.repository.SettingsRepository
import com.example.perjalanan.data.repository.SettingsRepositoryImpl
import com.example.perjalanan.data.repository.SosRepository
import com.example.perjalanan.data.repository.SosRepositoryImpl

/**
 * Service Locator container providing single instances of Repositories across the app.
 */
object AppContainer {
    val authRepository: AuthRepository by lazy { AuthRepositoryImpl() }
    val healthRepository: HealthRepository by lazy { HealthRepositoryImpl() }
    val sosRepository: SosRepository by lazy { SosRepositoryImpl() }
    val policeRepository: PoliceRepository by lazy { PoliceRepositoryImpl() }
    val policeRegistrationRepository: PoliceRegistrationRepository by lazy { PoliceRegistrationRepositoryImpl() }
    val settingsRepository: SettingsRepository by lazy { SettingsRepositoryImpl() }
}
