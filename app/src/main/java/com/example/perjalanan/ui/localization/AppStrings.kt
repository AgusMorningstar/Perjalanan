package com.example.perjalanan.ui.localization

import androidx.compose.runtime.staticCompositionLocalOf
import com.example.perjalanan.data.model.Language

interface AppStrings {
    // Navigation Bottom Bar
    val navHome: String
    val navMap: String
    val navProfile: String
    val navEmergency: String
    val navDashboard: String

    // Common
    val appName: String
    val back: String
    val close: String
    val later: String
    val critical: String
    val normal: String
    val save: String

    // Settings
    val settingsTitle: String
    val preferences: String
    val appTheme: String
    val themeLight: String
    val themeDark: String
    val themeSystem: String
    val language: String
    val langIndonesia: String
    val langEnglish: String
    val onboardingGuide: String
    val open: String
    val changeLanguageTitle: String
    val selectThemeTitle: String
    val appVersionInfo: String
    val appSecurityNotice: String

    // Home Screen (Citizen)
    val sosWatchStatus: String
    val espConnected: String
    val espDisconnected: String
    val espReceivingLive: String
    val espTapToConnect: String
    val btnManage: String
    val btnConnect: String
    val sosCardTitle: String
    val sosCardSubtitle: String
    val sosHint: String
    val quickActions: String
    val actionCall110: String
    val actionVictimMap: String
    val actionShare: String
    val actionPolice: String
    val heartRateTitle: String
    val motionSensorTitle: String
    val citizenSimTitle: String
    val citizenSimDesc: String
    val btnTestPopup: String

    // Map Screen
    val monitoringMapTitle: String
    val victimLocation: String
    val zoneRadiusDesc: String
    val btnStartNavigation: String
    val centeringLocation: String
    val openingBrowserMap: String

    // Police Dashboard Screen
    val policeDashboardTitle: String
    val statusOnline: String
    val statusOffline: String
    val summaryActive: String
    val summaryResolved: String
    val summaryStandbyUnits: String
    val sosSirenAlarm: String
    val sirenActiveDesc: String
    val sirenInactiveDesc: String
    val sirenVolume: String
    val btnStopAlarm: String
    val incomingSosReports: String
    val btnTestPoliceSos: String
    val btnAcceptHandleReport: String
    val statusReportHandled: String

    // Police Profile & User Profile
    val userProfileTitle: String
    val policeProfileTitle: String
    val accountInfo: String
    val dutyInfo: String
    val idNumber: String
    val dutyUnit: String
    val headquarters: String
    val appSettingsMenu: String
    val appSettingsSub: String
    val changePasswordMenu: String
    val changePasswordSub: String
    val logoutMenu: String
    val logoutSub: String
    val logoutDialogTitle: String
    val logoutDialogMessage: String
    val btnLogout: String
    val btnCancel: String

    // Emergency Pop-up Dialog (dialog_sos.xml)
    val sosDialogTag: String
    val sosDialogTitle: String
    val infoDistance: String
    val infoEta: String
    val infoHeartRate: String
    val btnAcceptAndViewLocation: String
    val btnMuteAlarm: String
}

object IndonesiaStrings : AppStrings {
    override val navHome = "Beranda"
    override val navMap = "Peta"
    override val navProfile = "Profil"
    override val navEmergency = "Darurat"
    override val navDashboard = "Dashboard"

    override val appName = "Perjalanan"
    override val back = "Kembali"
    override val close = "Tutup"
    override val later = "Nanti"
    override val critical = "Kritis"
    override val normal = "Normal"
    override val save = "Simpan"

    override val settingsTitle = "Pengaturan"
    override val preferences = "Preferensi"
    override val appTheme = "Tema Aplikasi"
    override val themeLight = "Terang"
    override val themeDark = "Gelap"
    override val themeSystem = "Ikuti Sistem"
    override val language = "Bahasa"
    override val langIndonesia = "Indonesia"
    override val langEnglish = "English"
    override val onboardingGuide = "Panduan Aplikasi (Onboarding)"
    override val open = "Buka"
    override val changeLanguageTitle = "Ubah Bahasa"
    override val selectThemeTitle = "Mode Tampilan"
    override val appVersionInfo = "PERJALANAN v1.0.0 (Compose Edition)"
    override val appSecurityNotice = "Dilindungi dengan enkripsi sinyal darurat modern."

    override val sosWatchStatus = "Status Jam Tangan SOS"
    override val espConnected = "ESP32-Pulse Terhubung"
    override val espDisconnected = "ESP32-Pulse Belum Terhubung"
    override val espReceivingLive = "Menerima data BPM & Gerakan real-time"
    override val espTapToConnect = "Ketuk untuk menyambungkan alat"
    override val btnManage = "Kelola"
    override val btnConnect = "Hubungkan"
    override val sosCardTitle = "Tombol Darurat SOS"
    override val sosCardSubtitle = "Tahan 3 detik saat dalam situasi bahaya"
    override val sosHint = "Tekan tombol untuk kirim sinyal darurat"
    override val quickActions = "Aksi Cepat"
    override val actionCall110 = "Darurat 110"
    override val actionVictimMap = "Peta Korban"
    override val actionShare = "Bagikan"
    override val actionPolice = "Polisi"
    override val heartRateTitle = "Detak Jantung"
    override val motionSensorTitle = "Sensor Gerak"
    override val citizenSimTitle = "Simulasi Korban di Sekitar Anda"
    override val citizenSimDesc = "Uji pop-up darurat radius 250m dari posisi Anda"
    override val btnTestPopup = "Uji Pop-up"

    override val monitoringMapTitle = "PETA MONITORING DARURAT"
    override val victimLocation = "Lokasi Korban"
    override val zoneRadiusDesc = "Radius Zona: ±150 meter"
    override val btnStartNavigation = "Mulai Navigasi"
    override val centeringLocation = "Memusatkan ke lokasi korban..."
    override val openingBrowserMap = "Membuka browser peta..."

    override val policeDashboardTitle = "Dashboard Kepolisian"
    override val statusOnline = "● ONLINE"
    override val statusOffline = "OFFLINE"
    override val summaryActive = "Aktif"
    override val summaryResolved = "Selesai"
    override val summaryStandbyUnits = "Unit Siaga"
    override val sosSirenAlarm = "Alarm Sirine SOS"
    override val sirenActiveDesc = "Perangkat AKTIF berbunyi"
    override val sirenInactiveDesc = "Alarm NONAKTIF"
    override val sirenVolume = "Volume Sirine Alarm"
    override val btnStopAlarm = "Matikan Alarm Sekarang"
    override val incomingSosReports = "Laporan Sinyal Darurat Masuk"
    override val btnTestPoliceSos = "Uji Pop-up SOS"
    override val btnAcceptHandleReport = "Terima & Tangani Laporan"
    override val statusReportHandled = "✓ Laporan Sedang Ditangani"

    override val userProfileTitle = "Profil Pengguna"
    override val policeProfileTitle = "Profil Petugas"
    override val accountInfo = "Informasi Akun"
    override val dutyInfo = "Informasi Kedinasan"
    override val idNumber = "Nomor Identitas"
    override val dutyUnit = "Unit Penugasan"
    override val headquarters = "Kantor Induk"
    override val appSettingsMenu = "Pengaturan Aplikasi"
    override val appSettingsSub = "Tema, notifikasi, dan preferensi aplikasi"
    override val changePasswordMenu = "Ubah Kata Sandi"
    override val changePasswordSub = "Perbarui kunci akses kedinasan"
    override val logoutMenu = "Keluar Akun"
    override val logoutSub = "Logout dari sesi perangkat ini"
    override val logoutDialogTitle = "Konfirmasi Keluar"
    override val logoutDialogMessage = "Apakah Anda yakin ingin keluar dari akun ini?"
    override val btnLogout = "Keluar"
    override val btnCancel = "Batal"

    override val sosDialogTag = "SINYAL SOS - PRIORITAS TINGGI"
    override val sosDialogTitle = "Laporan darurat baru"
    override val infoDistance = "Jarak"
    override val infoEta = "Estimasi"
    override val infoHeartRate = "Detak"
    override val btnAcceptAndViewLocation = "Terima & Lihat Lokasi"
    override val btnMuteAlarm = "Matikan Alarm"
}

object EnglishStrings : AppStrings {
    override val navHome = "Home"
    override val navMap = "Map"
    override val navProfile = "Profile"
    override val navEmergency = "Emergency"
    override val navDashboard = "Dashboard"

    override val appName = "Perjalanan"
    override val back = "Back"
    override val close = "Close"
    override val later = "Later"
    override val critical = "Critical"
    override val normal = "Normal"
    override val save = "Save"

    override val settingsTitle = "Settings"
    override val preferences = "Preferences"
    override val appTheme = "App Theme"
    override val themeLight = "Light"
    override val themeDark = "Dark"
    override val themeSystem = "System Default"
    override val language = "Language"
    override val langIndonesia = "Indonesia"
    override val langEnglish = "English"
    override val onboardingGuide = "App Guide (Onboarding)"
    override val open = "Open"
    override val changeLanguageTitle = "Change Language"
    override val selectThemeTitle = "Display Mode"
    override val appVersionInfo = "PERJALANAN v1.0.0 (Compose Edition)"
    override val appSecurityNotice = "Protected with modern emergency signal encryption."

    override val sosWatchStatus = "SOS Smartwatch Status"
    override val espConnected = "ESP32-Pulse Connected"
    override val espDisconnected = "ESP32-Pulse Disconnected"
    override val espReceivingLive = "Receiving real-time BPM & Motion data"
    override val espTapToConnect = "Tap to connect device"
    override val btnManage = "Manage"
    override val btnConnect = "Connect"
    override val sosCardTitle = "SOS Emergency Button"
    override val sosCardSubtitle = "Hold for 3 seconds in dangerous situations"
    override val sosHint = "Press the button to send emergency signal"
    override val quickActions = "Quick Actions"
    override val actionCall110 = "Emergency 110"
    override val actionVictimMap = "Victim Map"
    override val actionShare = "Share"
    override val actionPolice = "Police"
    override val heartRateTitle = "Heart Rate"
    override val motionSensorTitle = "Motion Sensor"
    override val citizenSimTitle = "Simulate Nearby Victim"
    override val citizenSimDesc = "Test emergency pop-up within 250m radius from you"
    override val btnTestPopup = "Test Pop-up"

    override val monitoringMapTitle = "EMERGENCY MONITORING MAP"
    override val victimLocation = "Victim Location"
    override val zoneRadiusDesc = "Zone Radius: ±150 meters"
    override val btnStartNavigation = "Start Navigation"
    override val centeringLocation = "Centering on victim location..."
    override val openingBrowserMap = "Opening map browser..."

    override val policeDashboardTitle = "Police Dashboard"
    override val statusOnline = "● ONLINE"
    override val statusOffline = "OFFLINE"
    override val summaryActive = "Active"
    override val summaryResolved = "Resolved"
    override val summaryStandbyUnits = "Standby Units"
    override val sosSirenAlarm = "SOS Siren Alarm"
    override val sirenActiveDesc = "Device is ACTIVELY sounding"
    override val sirenInactiveDesc = "Alarm is INACTIVE"
    override val sirenVolume = "Siren Alarm Volume"
    override val btnStopAlarm = "Stop Alarm Now"
    override val incomingSosReports = "Incoming Emergency SOS Reports"
    override val btnTestPoliceSos = "Test SOS Pop-up"
    override val btnAcceptHandleReport = "Accept & Handle Report"
    override val statusReportHandled = "✓ Report Under Handling"

    override val userProfileTitle = "User Profile"
    override val policeProfileTitle = "Officer Profile"
    override val accountInfo = "Account Information"
    override val dutyInfo = "Duty Information"
    override val idNumber = "Identity Number"
    override val dutyUnit = "Assigned Unit"
    override val headquarters = "Headquarters"
    override val appSettingsMenu = "App Settings"
    override val appSettingsSub = "Theme, notifications, and app preferences"
    override val changePasswordMenu = "Change Password"
    override val changePasswordSub = "Update official access credentials"
    override val logoutMenu = "Logout Account"
    override val logoutSub = "Logout from this device session"
    override val logoutDialogTitle = "Logout Confirmation"
    override val logoutDialogMessage = "Are you sure you want to log out of this account?"
    override val btnLogout = "Logout"
    override val btnCancel = "Cancel"

    override val sosDialogTag = "SOS SIGNAL - HIGH PRIORITY"
    override val sosDialogTitle = "New Emergency Report"
    override val infoDistance = "Distance"
    override val infoEta = "ETA"
    override val infoHeartRate = "Heartbeat"
    override val btnAcceptAndViewLocation = "Accept & View Location"
    override val btnMuteAlarm = "Mute Alarm"
}

fun getAppStrings(language: Language): AppStrings {
    return when (language) {
        Language.INDONESIA -> IndonesiaStrings
        Language.ENGLISH -> EnglishStrings
    }
}

val LocalAppStrings = staticCompositionLocalOf<AppStrings> { IndonesiaStrings }
