package com.example.perjalanan.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.perjalanan.ui.screens.auth.ChangePasswordScreen
import com.example.perjalanan.ui.screens.auth.ForgotPasswordScreen
import com.example.perjalanan.ui.screens.auth.LoginScreen
import com.example.perjalanan.ui.screens.auth.RedeemInviteScreen
import com.example.perjalanan.ui.screens.auth.RegisterScreen
import com.example.perjalanan.ui.screens.auth.RequestPoliceScreen
import com.example.perjalanan.ui.screens.auth.RequestPoliceSentScreen
import com.example.perjalanan.ui.screens.auth.VerifyCodeScreen
import com.example.perjalanan.ui.screens.emergency.EmergencyScreen
import com.example.perjalanan.ui.screens.home.HomeScreen
import com.example.perjalanan.ui.screens.map.MapScreen
import com.example.perjalanan.ui.screens.onboarding.OnboardingScreen
import com.example.perjalanan.ui.screens.police.PoliceDashboardScreen
import com.example.perjalanan.ui.screens.police.PoliceMapScreen
import com.example.perjalanan.ui.screens.police.PoliceProfileScreen
import com.example.perjalanan.ui.screens.profile.ProfileScreen
import com.example.perjalanan.ui.screens.settings.SettingsScreen

@Composable
fun AppNavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.Onboarding.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // ONBOARDING
        composable(Screen.Onboarding.route) {
            OnboardingScreen(
                onFinishOnboarding = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                }
            )
        }

        // LOGIN
        composable(Screen.Login.route) {
            LoginScreen(
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onNavigateToPoliceDashboard = {
                    navController.navigate(Screen.PoliceDashboard.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onNavigateToForgotPassword = {
                    navController.navigate(Screen.ForgotPassword.route)
                },
                onNavigateToRegister = {
                    navController.navigate(Screen.Register.route)
                }
            )
        }

        // FORGOT PASSWORD
        composable(Screen.ForgotPassword.route) {
            ForgotPasswordScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onOtpSent = { identifier, destination ->
                    navController.navigate(Screen.VerifyCode.createRoute(identifier, destination))
                }
            )
        }

        // REGISTER
        composable(Screen.Register.route) {
            RegisterScreen(
                onNavigateToLogin = {
                    navController.popBackStack()
                },
                onRegisterSuccess = {
                    // Setelah register berhasil, arahkan ke halaman tukar kode undangan
                    navController.navigate(Screen.RedeemInvite.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }

        // REDEEM INVITE CODE (Tukar Kode Undangan Polsek)
        composable(Screen.RedeemInvite.route) {
            RedeemInviteScreen(
                onSkipAsUser = {
                    // Lewati → masuk sebagai warga biasa
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.RedeemInvite.route) { inclusive = true }
                    }
                },
                onRedeemSuccess = {
                    // Kode berhasil ditukar → masuk sebagai polisi (bersihkan stack warga)
                    navController.navigate(Screen.PoliceDashboard.route) {
                        popUpTo(0) { inclusive = true }
                    }
                },
                onRequestCode = {
                    navController.navigate(Screen.RequestPolice.route)
                }
            )
        }

        // REQUEST KODE POLISI (Form kirim request ke admin)
        composable(Screen.RequestPolice.route) {
            RequestPoliceScreen(
                onNavigateBack = { navController.popBackStack() },
                onRequestSent = {
                    navController.navigate(Screen.RequestPoliceSent.route) {
                        popUpTo(Screen.RequestPolice.route) { inclusive = true }
                    }
                }
            )
        }

        // HALAMAN KONFIRMASI REQUEST TERKIRIM
        composable(Screen.RequestPoliceSent.route) {
            RequestPoliceSentScreen(
                onBackToRedeemInvite = {
                    navController.navigate(Screen.RedeemInvite.route) {
                        popUpTo(Screen.RedeemInvite.route) { inclusive = true }
                    }
                },
                onContinueAsUser = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        // HOME
        composable(Screen.Home.route) {
            HomeScreen(
                onNavigate = { screen ->
                    navController.navigate(screen.route) {
                        launchSingleTop = true
                    }
                }
            )
        }

        // MAP (Citizen)
        composable(Screen.Map.route) {
            MapScreen(
                onNavigate = { screen ->
                    navController.navigate(screen.route) {
                        launchSingleTop = true
                    }
                },
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        // PROFILE
        composable(Screen.Profile.route) {
            ProfileScreen(
                onNavigate = { screen ->
                    navController.navigate(screen.route) {
                        launchSingleTop = true
                    }
                },
                onLogoutSuccess = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        // EMERGENCY
        composable(Screen.Emergency.route) {
            EmergencyScreen(
                onNavigate = { screen ->
                    navController.navigate(screen.route) {
                        launchSingleTop = true
                    }
                }
            )
        }

        // SETTINGS
        composable(Screen.Settings.route) {
            SettingsScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToOnboarding = {
                    navController.navigate(Screen.Onboarding.route)
                }
            )
        }

        // POLICE DASHBOARD
        composable(Screen.PoliceDashboard.route) {
            PoliceDashboardScreen(
                onNavigateToPoliceProfile = {
                    navController.navigate(Screen.PoliceProfile.route)
                },
                onNavigateToMap = {
                    navController.navigate(Screen.PoliceMap.route) {
                        launchSingleTop = true
                    }
                }
            )
        }

        // POLICE MAP
        composable(Screen.PoliceMap.route) {
            PoliceMapScreen(
                onNavigateToDashboard = {
                    navController.navigate(Screen.PoliceDashboard.route) {
                        launchSingleTop = true
                        popUpTo(Screen.PoliceDashboard.route) { inclusive = false }
                    }
                },
                onNavigateToPoliceProfile = {
                    navController.navigate(Screen.PoliceProfile.route) {
                        launchSingleTop = true
                    }
                },
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        // POLICE PROFILE
        composable(Screen.PoliceProfile.route) {
            PoliceProfileScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToDashboard = {
                    navController.navigate(Screen.PoliceDashboard.route) {
                        launchSingleTop = true
                        popUpTo(Screen.PoliceDashboard.route) { inclusive = false }
                    }
                },
                onNavigateToMap = {
                    navController.navigate(Screen.PoliceMap.route) {
                        launchSingleTop = true
                    }
                },
                onNavigateToSettings = {
                    navController.navigate(Screen.Settings.route)
                },
                onNavigateToChangePassword = {
                    navController.navigate(Screen.ChangePassword.createRoute(""))
                },
                onLogoutSuccess = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        // VERIFY CODE
        composable(
            route = Screen.VerifyCode.route,
            arguments = listOf(
                navArgument("identifier") {
                    type = NavType.StringType
                    defaultValue = ""
                },
                navArgument("destination") {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) { backStackEntry ->
            val identifier = backStackEntry.arguments?.getString("identifier") ?: ""
            val destination = backStackEntry.arguments?.getString("destination") ?: ""
            VerifyCodeScreen(
                identifier = identifier,
                destination = destination,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToChangePassword = { resetToken ->
                    navController.navigate(Screen.ChangePassword.createRoute(resetToken))
                }
            )
        }

        // CHANGE PASSWORD
        composable(
            route = Screen.ChangePassword.route,
            arguments = listOf(
                navArgument("resetToken") {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) { backStackEntry ->
            val resetToken = backStackEntry.arguments?.getString("resetToken") ?: ""
            ChangePasswordScreen(
                resetToken = resetToken,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToLogin = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }
    }
}
