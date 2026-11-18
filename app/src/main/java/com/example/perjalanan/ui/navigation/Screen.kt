package com.example.perjalanan.ui.navigation

sealed class Screen(val route: String) {
    object Onboarding : Screen("onboarding")
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home")
    object Map : Screen("map")
    object Profile : Screen("profile")
    object Emergency : Screen("emergency")
    object Settings : Screen("settings")
    object PoliceDashboard : Screen("police_dashboard")
    object PoliceMap : Screen("police_map")
    object PoliceProfile : Screen("police_profile")
    object VerifyCode : Screen("verify_code")
    object ChangePassword : Screen("change_password")
}
