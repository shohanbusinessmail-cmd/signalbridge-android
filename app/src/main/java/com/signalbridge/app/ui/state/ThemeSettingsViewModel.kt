package com.signalbridge.app.ui.state

import com.signalbridge.app.app.SignalBridgeApplication
import com.signalbridge.app.domain.model.ThemeMode

class ThemeSettingsViewModel(app: SignalBridgeApplication) : ScreenViewModel(app) {

    data class UiState(
        val modes: List<ThemeMode> = listOf(ThemeMode.SYSTEM, ThemeMode.LIGHT, ThemeMode.DARK),
        val selected: ThemeMode = ThemeMode.SYSTEM,
        val isSaving: Boolean = false
    )

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    fun select(mode: ThemeMode) {
        _state.value = _state.value.copy(selected = mode)
    }

    fun apply() {
        _state.value = _state.value.copy(isSaving = true)
        launch {
            val prefs = app.dataStoreLoadPreferences()
            app.dataStoreSavePreferences(prefs.copy(themeMode = _state.value.selected))
            _state.value = _state.value.copy(isSaving = false)
        }
    }
}
