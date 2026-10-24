package com.signalbridge.app.ui.state

import com.signalbridge.app.app.SignalBridgeApplication
import com.signalbridge.app.domain.model.AppLanguage

class LanguageSettingsViewModel(app: SignalBridgeApplication) : ScreenViewModel(app) {

    data class UiState(
        val languages: List<AppLanguage> = listOf(AppLanguage.ENGLISH, AppLanguage.BENGALI),
        val selected: AppLanguage = AppLanguage.ENGLISH,
        val isSaving: Boolean = false
    )

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    fun select(language: AppLanguage) {
        _state.value = _state.value.copy(selected = language)
    }

    fun apply() {
        _state.value = _state.value.copy(isSaving = true)
        launch {
            val prefs = app.dataStoreLoadPreferences()
            app.dataStoreSavePreferences(prefs.copy(language = _state.value.selected))
            _state.value = _state.value.copy(isSaving = false)
        }
    }
}
