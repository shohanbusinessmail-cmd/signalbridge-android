package com.signalbridge.app.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigate
import com.signalbridge.app.app.SignalBridgeApplication
import com.signalbridge.app.ui.screens.*

/**
 * The single navigation host for the app. Each screen is a lazy composable so
 * screens are only instantiated when reached.
 */
@Composable
fun SignalBridgeNavGraph(app: SignalBridgeApplication) {
    val navController = remember { NavHostController() }
    // Expose to Activities/Tests via LocalLifecycleOwner if needed.

    val viewModelFactory = remember { ScreenViewModelFactory(app) }

    Scaffold(
        modifier = fillMaxSize()
    ) { paddingValues ->
        NavHost(navController, startDestination = NavRoutes.SPLASH) {
            // --- Onboarding flow ---
            composable(NavRoutes.SPLASH) {
                SplashScreen(viewModelFactory, onNavigate = { navController.navigate(it) })
            }
            composable(NavRoutes.WELCOME) {
                WelcomeScreen(viewModelFactory, onNavigate = { navController.navigate(it) })
            }
            composable(NavRoutes.CAPABILITY_INTRO) {
                CapabilityIntroScreen(viewModelFactory, onNavigate = { navController.navigate(it) })
            }
            composable(NavRoutes.PERMISSION_OVERVIEW) {
                PermissionOverviewScreen(viewModelFactory, onNavigate = { navController.navigate(it) })
            }
            composable(NavRoutes.SIM_DISCOVERY) {
                SimDiscoveryScreen(viewModelFactory, onNavigate = { navController.navigate(it) })
            }
            composable(NavRoutes.SIM_SELECTION) {
                SimSelectionScreen(viewModelFactory, onNavigate = { navController.navigate(it) })
            }
            composable(NavRoutes.SETUP_COMPLETION) {
                SetupCompletionScreen(viewModelFactory, onNavigate = { navController.navigate(it) })
            }

            // --- Main flow ---
            composable(NavRoutes.DASHBOARD) {
                DashboardScreen(viewModelFactory, onNavigate = { navController.navigate(it) })
            }
            composable("${NavRoutes.SIM_DETAIL}/{${NavArgs.SUBSCRIPTION_ID}}") {
                val subscriptionId = it.arguments?.getInt(NavArgs.SUBSCRIPTION_ID) ?: 0
                SimDetailScreen(viewModelFactory, subscriptionId, onNavigate = { navController.navigate(it) })
            }

            composable(NavRoutes.FORWARDING_WIZARD) {
                ForwardingWizardScreen(viewModelFactory, onNavigate = { navController.navigate(it) })
            }
            composable(NavRoutes.DESTINATION_PICKER) {
                DestinationPickerScreen(viewModelFactory, onNavigate = { navController.navigate(it) })
            }
            composable(NavRoutes.CONDITION_DETAILS) {
                ConditionDetailsScreen(viewModelFactory, onNavigate = { navController.navigate(it) })
            }
            composable(NavRoutes.QUICK_CONTROL) {
                QuickControlScreen(viewModelFactory, onNavigate = { navController.navigate(it) })
            }
            composable(NavRoutes.SMART_PROTECTION) {
                SmartProtectionScreen(viewModelFactory, onNavigate = { navController.navigate(it) })
            }
            composable(NavRoutes.PROFILES) {
                ProfilesScreen(viewModelFactory, onNavigate = { navController.navigate(it) })
            }
            composable("${NavRoutes.PROFILE_EDITOR}/{${NavArgs.PROFILE_ID}}") {
                val profileId = it.arguments?.getString(NavArgs.PROFILE_ID) ?: ""
                ProfileEditorScreen(viewModelFactory, profileId, onNavigate = { navController.navigate(it) })
            }
            composable(NavRoutes.DIAGNOSTICS) {
                DiagnosticsScreen(viewModelFactory, onNavigate = { navController.navigate(it) })
            }
            composable(NavRoutes.HISTORY) {
                HistoryScreen(viewModelFactory, onNavigate = { navController.navigate(it) })
            }
            composable(NavRoutes.NOTIFICATIONS) {
                NotificationCenterScreen(viewModelFactory, onNavigate = { navController.navigate(it) })
            }
            composable(NavRoutes.SETTINGS) {
                SettingsScreen(viewModelFactory, onNavigate = { navController.navigate(it) })
            }
            composable(NavRoutes.LANGUAGE_SETTINGS) {
                LanguageSettingsScreen(viewModelFactory, onNavigate = { navController.navigate(it) })
            }
            composable(NavRoutes.THEME_SETTINGS) {
                ThemeSettingsScreen(viewModelFactory, onNavigate = { navController.navigate(it) })
            }
            composable(NavRoutes.PERMISSION_RECOVERY) {
                PermissionRecoveryScreen(viewModelFactory, onNavigate = { navController.navigate(it) })
            }
            composable(NavRoutes.CARRIER_INSTRUCTIONS) {
                CarrierInstructionsScreen(viewModelFactory, onNavigate = { navController.navigate(it) })
            }
            composable(NavRoutes.TROUBLESHOOTING) {
                TroubleshootingScreen(viewModelFactory, onNavigate = { navController.navigate(it) })
            }
            composable(NavRoutes.ABOUT) {
                AboutScreen(viewModelFactory, onNavigate = { navController.navigate(it) })
            }
            composable(NavRoutes.PRIVACY) {
                PrivacyScreen(viewModelFactory, onNavigate = { navController.navigate(it) })
            }
        }
    }
}
