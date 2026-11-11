package com.example.perjalanan.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.perjalanan.ui.screens.auth.ChangePasswordScreen
import com.example.perjalanan.ui.screens.auth.LoginScreen
import com.example.perjalanan.ui.screens.auth.VerifyCodeScreen
import com.example.perjalanan.ui.screens.emergency.EmergencyScreen
import com.example.perjalanan.ui.screens.home.HomeScreen
import com.example.perjalanan.ui.screens.map.MapScreen
import com.example.perjalanan.ui.screens.police.PoliceDashboardScreen
import com.example.perjalanan.ui.screens.police.PoliceProfileScreen
import com.example.perjalanan.ui.screens.profile.ProfileScreen
import com.example.perjalanan.ui.screens.settings.SettingsScreen

@Composable
fun AppNavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.Login.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
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
                    navController.navigate(Screen.VerifyCode.route)
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

        // MAP
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
                }
            )
        }

        // POLICE DASHBOARD
        composable(Screen.PoliceDashboard.route) {
            PoliceDashboardScreen(
                onNavigateToPoliceProfile = {
                    navController.navigate(Screen.PoliceProfile.route)
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
                onNavigateToSettings = {
                    navController.navigate(Screen.Settings.route)
                },
                onNavigateToChangePassword = {
                    navController.navigate(Screen.ChangePassword.route)
                },
                onLogoutSuccess = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        // VERIFY CODE
        composable(Screen.VerifyCode.route) {
            VerifyCodeScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToChangePassword = {
                    navController.navigate(Screen.ChangePassword.route)
                }
            )
        }

        // CHANGE PASSWORD
        composable(Screen.ChangePassword.route) {
            ChangePasswordScreen(
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
