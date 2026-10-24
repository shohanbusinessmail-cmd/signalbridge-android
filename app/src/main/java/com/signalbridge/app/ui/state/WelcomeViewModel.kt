package com.signalbridge.app.ui.state

import com.signalbridge.app.app.SignalBridgeApplication
import com.signalbridge.app.domain.model.AppLanguage

class WelcomeViewModel(app: SignalBridgeApplication) : ScreenViewModel(app) {

    data class UiState(
        val selectedLanguage: AppLanguage = AppLanguage.ENGLISH,
        val isLoading: Boolean = false,
        val error: String? = null
    )

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    fun selectLanguage(language: AppLanguage) {
        _state.value = _state.value.copy(selectedLanguage = language)
    }

    fun confirm() {
        _state.value = _state.value.copy(isLoading = true)
        launch {
            try {
                val prefs = app.dataStoreLoadPreferences()
                app.dataStoreSavePreferences(prefs.copy(language = _state.value.selectedLanguage))
                _state.value = _state.value.copy(isLoading = false)
            } catch (e: Exception) {
                _state.value = _state.value.copy(isLoading = false, error = e.message)
            }
        }
    }
}
