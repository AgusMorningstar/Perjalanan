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
    object ForgotPassword : Screen("forgot_password")
    object VerifyCode : Screen("verify_code?identifier={identifier}&destination={destination}") {
        fun createRoute(identifier: String, destination: String): String {
            val encodedId = java.net.URLEncoder.encode(identifier, "UTF-8")
            val encodedDest = java.net.URLEncoder.encode(destination, "UTF-8")
            return "verify_code?identifier=$encodedId&destination=$encodedDest"
        }
    }
    object ChangePassword : Screen("change_password?resetToken={resetToken}") {
        fun createRoute(resetToken: String): String {
            val encodedToken = java.net.URLEncoder.encode(resetToken, "UTF-8")
            return "change_password?resetToken=$encodedToken"
        }
    }
    object RedeemInvite : Screen("redeem_invite")
    object RequestPolice : Screen("request_police")
    object RequestPoliceSent : Screen("request_police_sent")
}
