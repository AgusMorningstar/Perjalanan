package com.example.perjalanan.ui.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Home : Screen("home")
    object Map : Screen("map")
    object Profile : Screen("profile")
    object Emergency : Screen("emergency")
    object Settings : Screen("settings")
    object PoliceDashboard : Screen("police_dashboard")
    object PoliceProfile : Screen("police_profile")
    object VerifyCode : Screen("verify_code")
    object ChangePassword : Screen("change_password")
}
