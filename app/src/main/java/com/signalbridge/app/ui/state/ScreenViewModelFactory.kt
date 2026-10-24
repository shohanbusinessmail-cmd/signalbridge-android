package com.signalbridge.app.ui.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.signalbridge.app.app.SignalBridgeApplication

/**
 * Simple hand-wired ViewModel factory. Each screen's ViewModel receives the
 * application so it can reach repositories and the gateway.
 */
class ScreenViewModelFactory(private val app: SignalBridgeApplication) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SplashViewModel::class.java) ->
                SplashViewModel(app) as T
            modelClass.isAssignableFrom(WelcomeViewModel::class.java) ->
                WelcomeViewModel(app) as T
            modelClass.isAssignableFrom(CapabilityIntroViewModel::class.java) ->
                CapabilityIntroViewModel(app) as T
            modelClass.isAssignableFrom(PermissionOverviewViewModel::class.java) ->
                PermissionOverviewViewModel(app) as T
            modelClass.isAssignableFrom(SimDiscoveryViewModel::class.java) ->
                SimDiscoveryViewModel(app) as T
            modelClass.isAssignableFrom(SimSelectionViewModel::class.java) ->
                SimSelectionViewModel(app) as T
            modelClass.isAssignableFrom(DashboardViewModel::class.java) ->
                DashboardViewModel(app) as T
            modelClass.isAssignableFrom(SimDetailViewModel::class.java) ->
                SimDetailViewModel(app) as T
            modelClass.isAssignableFrom(ForwardingWizardViewModel::class.java) ->
                ForwardingWizardViewModel(app) as T
            modelClass.isAssignableFrom(DestinationPickerViewModel::class.java) ->
                DestinationPickerViewModel(app) as T
            modelClass.isAssignableFrom(ConditionDetailsViewModel::class.java) ->
                ConditionDetailsViewModel(app) as T
            modelClass.isAssignableFrom(QuickControlViewModel::class.java) ->
                QuickControlViewModel(app) as T
            modelClass.isAssignableFrom(SmartProtectionViewModel::class.java) ->
                SmartProtectionViewModel(app) as T
            modelClass.isAssignableFrom(ProfilesViewModel::class.java) ->
                ProfilesViewModel(app) as T
            modelClass.isAssignableFrom(ProfileEditorViewModel::class.java) ->
                ProfileEditorViewModel(app) as T
            modelClass.isAssignableFrom(DiagnosticsViewModel::class.java) ->
                DiagnosticsViewModel(app) as T
            modelClass.isAssignableFrom(HistoryViewModel::class.java) ->
                HistoryViewModel(app) as T
            modelClass.isAssignableFrom(NotificationCenterViewModel::class.java) ->
                NotificationCenterViewModel(app) as T
            modelClass.isAssignableFrom(SettingsViewModel::class.java) ->
                SettingsViewModel(app) as T
            modelClass.isAssignableFrom(LanguageSettingsViewModel::class.java) ->
                LanguageSettingsViewModel(app) as T
            modelClass.isAssignableFrom(ThemeSettingsViewModel::class.java) ->
                ThemeSettingsViewModel(app) as T
            modelClass.isAssignableFrom(PermissionRecoveryViewModel::class.java) ->
                PermissionRecoveryViewModel(app) as T
            modelClass.isAssignableFrom(CarrierInstructionsViewModel::class.java) ->
                CarrierInstructionsViewModel(app) as T
            modelClass.isAssignableFrom(TroubleshootingViewModel::class.java) ->
                TroubleshootingViewModel(app) as T
            modelClass.isAssignableFrom(AboutViewModel::class.java) ->
                AboutViewModel(app) as T
            modelClass.isAssignableFrom(PrivacyViewModel::class.java) ->
                PrivacyViewModel(app) as T
            modelClass.isAssignableFrom(SetupCompletionViewModel::class.java) ->
                SetupCompletionViewModel(app) as T
            else -> throw IllegalArgumentException("Unknown ViewModel: $modelClass")
        }
    }
}
