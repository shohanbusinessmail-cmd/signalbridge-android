package com.signalbridge.app.ui.state

import com.signalbridge.app.app.SignalBridgeApplication
import com.signalbridge.app.domain.model.AppLanguage
import com.signalbridge.app.domain.model.ThemeMode
import com.signalbridge.app.domain.model.RecoveryPolicy

class SettingsViewModel(app: SignalBridgeApplication) : ScreenViewModel(app) {

    data class UiState(
        val language: AppLanguage = AppLanguage.ENGLISH,
        val themeMode: ThemeMode = ThemeMode.SYSTEM,
        val defaultRecoveryPolicy: RecoveryPolicy = RecoveryPolicy.default,
        val isLoading: Boolean = false
    )

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    fun load() {
        launch {
            val prefs = app.dataStoreLoadPreferences()
            _state.value = UiState(
                language = prefs.language,
                themeMode = prefs.themeMode,
                defaultRecoveryPolicy = prefs.defaultRecoveryPolicy
            )
        }
    }

    fun saveLanguage(language: AppLanguage) {
        launch {
            val prefs = app.dataStoreLoadPreferences()
            app.dataStoreSavePreferences(prefs.copy(language = language))
        }
    }

    fun saveTheme(mode: ThemeMode) {
        launch {
            val prefs = app.dataStoreLoadPreferences()
            app.dataStoreSavePreferences(prefs.copy(themeMode = mode))
        }
    }
}
