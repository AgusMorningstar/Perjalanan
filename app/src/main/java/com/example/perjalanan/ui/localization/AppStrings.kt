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
    val helloGreeting: String
    val monitoringActive: String
    val securityStatusLabel: String
    val statusProtected: String
    val securitySafe: String
    val smartwatchMonitoring: String
    val heartRateWaiting: String
    val motionStill: String
    val motionDeviceDisconnected: String
    val powerStatusLabel: String
    val powerUsbActive: String
    val powerBatteryNormal: String
    val powerDeviceDisconnected: String
    val gpsLocationLabel: String
    val gpsActiveText: String
    val mpuTelemetryTitle: String
    val accelerationLabel: String
    val gyroscopeLabel: String
    val sosEmergencyLabel: String
    val minutesAbbr: String

    // Map Screen
    val monitoringMapTitle: String
    val victimLocation: String
    val zoneRadiusDesc: String
    val btnStartNavigation: String
    val centeringLocation: String
    val openingBrowserMap: String
    val coordinatesLabel: String
    val myLocation: String

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
    val locationPrefix: String
    val criticalSpike: String

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

    // Account & Profile Details
    val fullName: String
    val emailLabel: String
    val phoneLabel: String
    val passwordLabel: String
    val roleLabel: String
    val roleUserText: String
    val rolePoliceText: String
    val notSet: String
    val defaultUserName: String
    val inviteCodeMenuTitle: String
    val inviteCodeMenuSub: String

    // Edit Profile Field Dialog
    val editNameTitle: String
    val editEmailTitle: String
    val editPhoneTitle: String
    val editSecurityNotice: String
    val accountPasswordLabel: String
    val accountPasswordPlaceholder: String
    val fieldCannotBeEmpty: String
    val passwordRequiredConfirmation: String
    val btnSaveText: String
    val btnCancelText: String
    val btnUpdate: String

    // Change Password Dialog & Screen
    val changePasswordDialogTitle: String
    val changePasswordDialogNotice: String
    val oldPasswordLabel: String
    val newPasswordLabel: String
    val confirmNewPasswordLabel: String
    val oldPasswordRequired: String
    val newPasswordMinLength: String
    val passwordMismatch: String
    val newPasswordScreenDesc: String

    // Police Profile & Alarm Dialog
    val alarmSettingsTitle: String
    val currentAlarmStatusLabel: String
    val statusActive: String
    val statusInactive: String
    val alarmControlAction: String
    val activateAlarmAction: String
    val deactivateAlarmAction: String
    val policeDutyStatusStandby: String

    // Emergency Screen
    val emergencyCallTitle: String
    val policeInstitution: String
    val callNowPrefix: String
    val emergencyGuideTitle: String
    val policeTollFree: String
    val emergencyGuideDesc: String

    // Redeem Invite Screen
    val redeemScreenTitle: String
    val redeemScreenDesc: String
    val howToGetCodeTitle: String
    val howToGetCodeDesc: String
    val inviteCodeLabel: String
    val inviteCodePlaceholder: String
    val btnActivatePolice: String
    val btnSkipAsCitizen: String
    val btnRequestCode: String

    // Request Police Screen (Form kirim data ke admin)
    val requestPoliceTitle: String
    val requestPoliceSubtitle: String
    val requestPoliceInfo: String
    val requestPolsekLabel: String
    val requestPolsekPlaceholder: String
    val requestFullNameLabel: String
    val requestFullNamePlaceholder: String
    val requestNrpLabel: String
    val requestNrpPlaceholder: String
    val requestPhoneLabel: String
    val requestPhonePlaceholder: String
    val requestReasonLabel: String
    val requestReasonPlaceholder: String
    val btnSendPoliceRequest: String

    // Request Police Sent Screen (konfirmasi)
    val requestSentTitle: String
    val requestSentDesc: String
    val requestSentEmailHint: String
    val btnIHaveCode: String

    // Auth Screens (Login / Register / OTP)
    val orDivider: String
    val signInWithGoogle: String
    val signUpWithGoogle: String
    val noAccountPrompt: String
    val haveAccountPrompt: String
    val registerLink: String
    val loginLink: String
    val demoCitizen: String
    val demoPolice: String
    val forgotPasswordScreenTitle: String
    val forgotPasswordScreenSubtitle: String
    val identifierInputLabel: String
    val identifierPlaceholder: String
    val btnSendOtp: String
    val backToLogin: String
    val verifyCodeTitle: String
    val enterOtpTitle: String
    val otpSentDesc: String
    val resendCodeTimer: String
    val resendCode: String
    val btnVerify: String

    // BLE Dialog
    val connectDeviceTitle: String
    val rescan: String
    val connectedStatus: String
    val connectingTo: String
    val scanningNearby: String
    val connectionFailedDesc: String
    val selectBleDevice: String
    val btnDisconnect: String

    // Emergency Pop-up Dialog (dialog_sos.xml)
    val sosDialogTag: String
    val sosDialogTitle: String
    val infoDistance: String
    val infoEta: String
    val infoHeartRate: String
    val btnAcceptAndViewLocation: String
    val btnMuteAlarm: String

    // SOS Confirmation Dialog (Citizen)
    val sosSendConfirmTitle: String
    val sosSendConfirmBody: String
    val btnSendNow: String

    // Notifications Dialog (Citizen)
    val notificationTitle: String
    val notificationEmpty: String
    val notificationSystemActive: String
    val notificationSystemActiveDesc: String
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
    override val helloGreeting = "Halo"
    override val monitoringActive = "Layanan Pemantauan Aktif"
    override val securityStatusLabel = "Status Keamanan"
    override val statusProtected = "● Terproteksi"
    override val securitySafe = "Aman"
    override val smartwatchMonitoring = "Pemantauan Smartwatch"
    override val heartRateWaiting = "Menunggu Sensor"
    override val motionStill = "Diam"
    override val motionDeviceDisconnected = "Alat terputus"
    override val powerStatusLabel = "Status Daya Alat"
    override val powerUsbActive = "Daya USB Aktif"
    override val powerBatteryNormal = "Baterai Normal"
    override val powerDeviceDisconnected = "Alat belum tersambung"
    override val gpsLocationLabel = "Lokasi GPS"
    override val gpsActiveText = "Aktif"
    override val mpuTelemetryTitle = "Telemetri MPU6050 Real-time"
    override val accelerationLabel = "Akselerasi (g):"
    override val gyroscopeLabel = "Gyroscope (°/s):"
    override val sosEmergencyLabel = "DARURAT"
    override val minutesAbbr = "mnt"

    override val monitoringMapTitle = "PETA MONITORING DARURAT"
    override val victimLocation = "Lokasi Korban"
    override val zoneRadiusDesc = "Radius Zona: ±150 meter"
    override val btnStartNavigation = "Mulai Navigasi"
    override val centeringLocation = "Memusatkan ke lokasi korban..."
    override val openingBrowserMap = "Membuka browser peta..."
    override val coordinatesLabel = "Koordinat"
    override val myLocation = "Lokasi Saya"

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
    override val locationPrefix = "Lokasi"
    override val criticalSpike = "Lonjakan Kritis"

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

    override val fullName = "Nama Lengkap"
    override val emailLabel = "Email"
    override val phoneLabel = "Nomor Telepon"
    override val passwordLabel = "Password"
    override val roleLabel = "Role"
    override val roleUserText = "PENGGUNA"
    override val rolePoliceText = "POLISI"
    override val notSet = "Belum diatur"
    override val defaultUserName = "Pengguna"
    override val inviteCodeMenuTitle = "Kode Undangan Polsek"
    override val inviteCodeMenuSub = "Tukar kode untuk akses petugas polisi"

    override val editNameTitle = "Ubah Nama Lengkap"
    override val editEmailTitle = "Ubah Email"
    override val editPhoneTitle = "Ubah Nomor Telepon"
    override val editSecurityNotice = "Perubahan informasi akun memerlukan konfirmasi kata sandi Anda demi keamanan."
    override val accountPasswordLabel = "Kata Sandi Akun"
    override val accountPasswordPlaceholder = "Masukkan sandi untuk konfirmasi"
    override val fieldCannotBeEmpty = "tidak boleh kosong"
    override val passwordRequiredConfirmation = "Kata sandi wajib diisi untuk konfirmasi"
    override val btnSaveText = "Simpan"
    override val btnCancelText = "Batal"
    override val btnUpdate = "Perbarui"

    override val changePasswordDialogTitle = "Ubah Kata Sandi"
    override val changePasswordDialogNotice = "Masukkan kata sandi lama Anda untuk konfirmasi, lalu masukkan kata sandi baru (minimal 8 karakter)."
    override val oldPasswordLabel = "Kata Sandi Lama"
    override val newPasswordLabel = "Kata Sandi Baru"
    override val confirmNewPasswordLabel = "Konfirmasi Kata Sandi Baru"
    override val oldPasswordRequired = "Kata sandi lama wajib diisi"
    override val newPasswordMinLength = "Kata sandi baru minimal 8 karakter"
    override val passwordMismatch = "Konfirmasi kata sandi baru tidak cocok"
    override val newPasswordScreenDesc = "Buat kata sandi baru yang kuat minimal 8 karakter."

    override val alarmSettingsTitle = "Pengaturan Alarm SOS"
    override val currentAlarmStatusLabel = "Status Alarm Saat Ini:"
    override val statusActive = "AKTIF"
    override val statusInactive = "NONAKTIF"
    override val alarmControlAction = "Pilih aksi pengontrolan sirine darurat:"
    override val activateAlarmAction = "• Aktifkan Sirine Alarm"
    override val deactivateAlarmAction = "• Matikan Sirine Alarm"
    override val policeDutyStatusStandby = "Siaga"

    override val emergencyCallTitle = "Panggilan Darurat"
    override val policeInstitution = "Kepolisian Republik Indonesia"
    override val callNowPrefix = "Hubungi"
    override val emergencyGuideTitle = "Petunjuk Darurat"
    override val policeTollFree = "Layanan Polisi Bebas Pulsa 24 Jam"
    override val emergencyGuideDesc = "Tekan tombol panggil untuk segera terhubung dengan petugas kepolisian terdekat dari koordinat Anda."

    override val redeemScreenTitle = "Apakah Anda Petugas Polisi?"
    override val redeemScreenDesc = "Jika Anda memiliki kode undangan dari Polsek, masukkan kode di bawah untuk mengaktifkan akses petugas kepolisian."
    override val howToGetCodeTitle = "Cara Mendapatkan Kode"
    override val howToGetCodeDesc = "Kode undangan dikirim oleh admin Polsek Anda melalui email. Cek kotak masuk (inbox) atau folder spam di email Anda. Setiap kode bersifat unik, hanya dapat digunakan 1 kali, dan memiliki masa berlaku terbatas."
    override val inviteCodeLabel = "Kode Undangan Polsek"
    override val inviteCodePlaceholder = "Contoh: POL7X9M2KB4"
    override val btnActivatePolice = "Aktifkan Akses Polisi"
    override val btnSkipAsCitizen = "Lewati, Lanjut Sebagai Warga"
    override val btnRequestCode = "Belum Punya Kode? Minta ke Admin"

    override val requestPoliceTitle = "Daftar Sebagai Polisi"
    override val requestPoliceSubtitle = "Isi data diri Anda. Admin Polsek akan memverifikasi dan mengirimkan kode aktivasi ke email Anda."
    override val requestPoliceInfo = "Kode aktivasi akan dikirim ke email akun Anda setelah admin memverifikasi data. Proses verifikasi memerlukan waktu."
    override val requestPolsekLabel = "Nama Polsek"
    override val requestPolsekPlaceholder = "Contoh: Polsek Sukasari"
    override val requestFullNameLabel = "Nama Lengkap"
    override val requestFullNamePlaceholder = "Sesuai dokumen resmi"
    override val requestNrpLabel = "NRP / NIP"
    override val requestNrpPlaceholder = "Nomor Registrasi Pokok"
    override val requestPhoneLabel = "Nomor HP (opsional)"
    override val requestPhonePlaceholder = "Contoh: 08123456789"
    override val requestReasonLabel = "Keterangan (opsional)"
    override val requestReasonPlaceholder = "Misal: mutasi dari Polsek lain"
    override val btnSendPoliceRequest = "Kirim Request ke Admin"

    override val requestSentTitle = "Request Terkirim!"
    override val requestSentDesc = "Data Anda telah dikirim ke admin Polsek. Admin akan memverifikasi dan mengirimkan kode aktivasi ke email Anda jika disetujui."
    override val requestSentEmailHint = "Pantau email Anda (termasuk folder Spam). Setelah menerima kode, kembali ke layar ini dan masukkan kode tersebut."
    override val btnIHaveCode = "Sudah Punya Kode, Masukkan Sekarang"

    override val orDivider = "atau"
    override val signInWithGoogle = "Login dengan Google"
    override val signUpWithGoogle = "Daftar dengan Google"
    override val noAccountPrompt = "Belum punya akun? "
    override val haveAccountPrompt = "Sudah punya akun? "
    override val registerLink = "Daftar"
    override val loginLink = "Login"
    override val demoCitizen = "Demo Korban"
    override val demoPolice = "Demo Polisi"
    override val forgotPasswordScreenTitle = "Lupa Kata Sandi"
    override val forgotPasswordScreenSubtitle = "Masukkan alamat email akun Anda. Kami akan mengirimkan 4 digit kode OTP verifikasi ke email tersebut."
    override val identifierInputLabel = "Alamat Email"
    override val identifierPlaceholder = "contoh: user@gmail.com"
    override val btnSendOtp = "Kirim Kode OTP"
    override val backToLogin = "Kembali ke Halaman Masuk"
    override val verifyCodeTitle = "Verifikasi Kode"
    override val enterOtpTitle = "Masukkan Kode OTP"
    override val otpSentDesc = "Kami telah mengirimkan 4 digit kode verifikasi ke email Anda."
    override val resendCodeTimer = "Kirim ulang kode"
    override val resendCode = "Kirim ulang kode"
    override val btnVerify = "Verifikasi"

    override val connectDeviceTitle = "Sambungkan Alat"
    override val rescan = "Pindai Ulang"
    override val connectedStatus = "Terhubung!"
    override val connectingTo = "Menghubungkan ke"
    override val scanningNearby = "Mencari perangkat Bluetooth di sekitar..."
    override val connectionFailedDesc = "Koneksi gagal. Pastikan ESP32 sudah menyala."
    override val selectBleDevice = "Pilih perangkat Bluetooth untuk disambungkan:"
    override val btnDisconnect = "Putuskan"

    override val sosDialogTag = "SINYAL SOS - PRIORITAS TINGGI"
    override val sosDialogTitle = "Laporan darurat baru"
    override val infoDistance = "Jarak"
    override val infoEta = "Estimasi"
    override val infoHeartRate = "Detak"
    override val btnAcceptAndViewLocation = "Terima & Lihat Lokasi"
    override val btnMuteAlarm = "Matikan Alarm"

    override val sosSendConfirmTitle = "Kirim sinyal SOS?"
    override val sosSendConfirmBody = "Sinyal darurat beserta lokasi Anda saat ini akan segera dikirimkan ke kepolisian dan kontak darurat."
    override val btnSendNow = "Kirim Sekarang"

    override val notificationTitle = "Notifikasi"
    override val notificationEmpty = "Tidak ada notifikasi baru saat ini."
    override val notificationSystemActive = "Sistem Pengawasan Aktif"
    override val notificationSystemActiveDesc = "Smartwatch SOS dan koneksi server siap siaga memantau keamanan Anda."
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
    override val helloGreeting = "Hello"
    override val monitoringActive = "Active Monitoring Service"
    override val securityStatusLabel = "Security Status"
    override val statusProtected = "● Protected"
    override val securitySafe = "Safe"
    override val smartwatchMonitoring = "Smartwatch Monitoring"
    override val heartRateWaiting = "Waiting for Sensor"
    override val motionStill = "Still"
    override val motionDeviceDisconnected = "Device disconnected"
    override val powerStatusLabel = "Device Power Status"
    override val powerUsbActive = "USB Power Active"
    override val powerBatteryNormal = "Battery Normal"
    override val powerDeviceDisconnected = "Device not connected"
    override val gpsLocationLabel = "GPS Location"
    override val gpsActiveText = "Active"
    override val mpuTelemetryTitle = "Real-time MPU6050 Telemetry"
    override val accelerationLabel = "Acceleration (g):"
    override val gyroscopeLabel = "Gyroscope (°/s):"
    override val sosEmergencyLabel = "EMERGENCY"
    override val minutesAbbr = "mins"

    override val monitoringMapTitle = "EMERGENCY MONITORING MAP"
    override val victimLocation = "Victim Location"
    override val zoneRadiusDesc = "Zone Radius: ±150 meters"
    override val btnStartNavigation = "Start Navigation"
    override val centeringLocation = "Centering on victim location..."
    override val openingBrowserMap = "Opening map browser..."
    override val coordinatesLabel = "Coordinates"
    override val myLocation = "My Location"

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
    override val locationPrefix = "Location"
    override val criticalSpike = "Critical Spike"

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

    override val fullName = "Full Name"
    override val emailLabel = "Email"
    override val phoneLabel = "Phone Number"
    override val passwordLabel = "Password"
    override val roleLabel = "Role"
    override val roleUserText = "CITIZEN"
    override val rolePoliceText = "POLICE"
    override val notSet = "Not set"
    override val defaultUserName = "User"
    override val inviteCodeMenuTitle = "Police Invite Code"
    override val inviteCodeMenuSub = "Redeem code for police officer access"

    override val editNameTitle = "Edit Full Name"
    override val editEmailTitle = "Edit Email"
    override val editPhoneTitle = "Edit Phone Number"
    override val editSecurityNotice = "Account information changes require password confirmation for security."
    override val accountPasswordLabel = "Account Password"
    override val accountPasswordPlaceholder = "Enter password to confirm"
    override val fieldCannotBeEmpty = "cannot be empty"
    override val passwordRequiredConfirmation = "Password is required for confirmation"
    override val btnSaveText = "Save"
    override val btnCancelText = "Cancel"
    override val btnUpdate = "Update"

    override val changePasswordDialogTitle = "Change Password"
    override val changePasswordDialogNotice = "Enter your current password to confirm, then enter a new password (min. 8 characters)."
    override val oldPasswordLabel = "Current Password"
    override val newPasswordLabel = "New Password"
    override val confirmNewPasswordLabel = "Confirm New Password"
    override val oldPasswordRequired = "Current password is required"
    override val newPasswordMinLength = "New password must be at least 8 characters"
    override val passwordMismatch = "New password confirmation does not match"
    override val newPasswordScreenDesc = "Create a strong new password with at least 8 characters."

    override val alarmSettingsTitle = "SOS Alarm Settings"
    override val currentAlarmStatusLabel = "Current Alarm Status:"
    override val statusActive = "ACTIVE"
    override val statusInactive = "INACTIVE"
    override val alarmControlAction = "Select emergency siren control action:"
    override val activateAlarmAction = "• Activate Siren Alarm"
    override val deactivateAlarmAction = "• Turn Off Siren Alarm"
    override val policeDutyStatusStandby = "On Duty"

    override val emergencyCallTitle = "Emergency Call"
    override val policeInstitution = "Indonesian National Police"
    override val callNowPrefix = "Call"
    override val emergencyGuideTitle = "Emergency Instructions"
    override val policeTollFree = "24-Hour Toll-Free Police Service"
    override val emergencyGuideDesc = "Press call button to immediately connect with the nearest police station from your coordinates."

    override val redeemScreenTitle = "Are You a Police Officer?"
    override val redeemScreenDesc = "If you have an invite code from a police precinct, enter the code below to activate officer access."
    override val howToGetCodeTitle = "How to Get a Code"
    override val howToGetCodeDesc = "Your precinct administrator will send the invite code to your email. Check your inbox or spam folder. Each code is unique, can only be used once, and has an expiration date."
    override val inviteCodeLabel = "Precinct Invite Code"
    override val inviteCodePlaceholder = "e.g. POL7X9M2KB4"
    override val btnActivatePolice = "Activate Police Access"
    override val btnSkipAsCitizen = "Skip, Continue as Citizen"
    override val btnRequestCode = "No Code Yet? Request from Admin"

    override val requestPoliceTitle = "Register as Police Officer"
    override val requestPoliceSubtitle = "Fill in your details. The precinct admin will verify and send an activation code to your email."
    override val requestPoliceInfo = "An activation code will be sent to your account email after admin verification. The process may take some time."
    override val requestPolsekLabel = "Precinct Name"
    override val requestPolsekPlaceholder = "e.g. Polsek Sukasari"
    override val requestFullNameLabel = "Full Name"
    override val requestFullNamePlaceholder = "As per official documents"
    override val requestNrpLabel = "NRP / NIP"
    override val requestNrpPlaceholder = "Police Registration Number"
    override val requestPhoneLabel = "Phone Number (optional)"
    override val requestPhonePlaceholder = "e.g. 08123456789"
    override val requestReasonLabel = "Notes (optional)"
    override val requestReasonPlaceholder = "e.g. transferred from another precinct"
    override val btnSendPoliceRequest = "Send Request to Admin"

    override val requestSentTitle = "Request Sent!"
    override val requestSentDesc = "Your data has been sent to the precinct admin. They will verify and send an activation code to your email if approved."
    override val requestSentEmailHint = "Monitor your email (including Spam folder). Once you receive the code, return to this screen and enter it."
    override val btnIHaveCode = "I Have a Code, Enter Now"

    override val orDivider = "or"
    override val signInWithGoogle = "Sign in with Google"
    override val signUpWithGoogle = "Sign up with Google"
    override val noAccountPrompt = "Don't have an account? "
    override val haveAccountPrompt = "Already have an account? "
    override val registerLink = "Register"
    override val loginLink = "Login"
    override val demoCitizen = "Demo Citizen"
    override val demoPolice = "Demo Police"
    override val forgotPasswordScreenTitle = "Forgot Password"
    override val forgotPasswordScreenSubtitle = "Enter your account's email address. We will send a 4-digit OTP verification code to your email."
    override val identifierInputLabel = "Email Address"
    override val identifierPlaceholder = "e.g. user@gmail.com"
    override val btnSendOtp = "Send OTP Code"
    override val backToLogin = "Back to Login"
    override val verifyCodeTitle = "Verify Code"
    override val enterOtpTitle = "Enter OTP Code"
    override val otpSentDesc = "We have sent a 4-digit verification code to your email."
    override val resendCodeTimer = "Resend code"
    override val resendCode = "Resend code"
    override val btnVerify = "Verify"

    override val connectDeviceTitle = "Connect Device"
    override val rescan = "Scan Again"
    override val connectedStatus = "Connected!"
    override val connectingTo = "Connecting to"
    override val scanningNearby = "Searching for nearby Bluetooth devices..."
    override val connectionFailedDesc = "Connection failed. Please ensure ESP32 is powered on."
    override val selectBleDevice = "Select a Bluetooth device to connect:"
    override val btnDisconnect = "Disconnect"

    override val sosDialogTag = "SOS SIGNAL - HIGH PRIORITY"
    override val sosDialogTitle = "New Emergency Report"
    override val infoDistance = "Distance"
    override val infoEta = "ETA"
    override val infoHeartRate = "Heartbeat"
    override val btnAcceptAndViewLocation = "Accept & View Location"
    override val btnMuteAlarm = "Mute Alarm"

    override val sosSendConfirmTitle = "Send SOS signal?"
    override val sosSendConfirmBody = "An emergency signal along with your current location will be immediately sent to police and emergency contacts."
    override val btnSendNow = "Send Now"

    override val notificationTitle = "Notifications"
    override val notificationEmpty = "No new notifications at this time."
    override val notificationSystemActive = "Monitoring System Active"
    override val notificationSystemActiveDesc = "SOS smartwatch and server connection are standby monitoring your safety."
}

fun getAppStrings(language: Language): AppStrings {
    return when (language) {
        Language.INDONESIA -> IndonesiaStrings
        Language.ENGLISH -> EnglishStrings
    }
}

val LocalAppStrings = staticCompositionLocalOf<AppStrings> { IndonesiaStrings }
