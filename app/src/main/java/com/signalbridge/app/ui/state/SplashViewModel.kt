package com.signalbridge.app.ui.state

import com.signalbridge.app.app.SignalBridgeApplication
import kotlinx.coroutines.flow.*

class SplashViewModel(app: SignalBridgeApplication) : ScreenViewModel(app) {

    sealed class UiState {
        object Loading : UiState()
        data class Ready(val setupComplete: Boolean, val languageSelected: Boolean) : UiState()
        data class Error(val message: String) : UiState()
    }

    private val _state = MutableStateFlow(UiState.Loading)
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        launch {
            try {
                val prefs = app.coroutineScope.coroutineContext
                // Load preferences asynchronously.
                val appPrefs = withContext(dispatchers.background) {
                    app.dataStoreLoadPreferences()
                }
                _state.value = UiState.Ready(
                    setupComplete = appPrefs.setupComplete,
                    languageSelected = true // language always has a default
                )
            } catch (e: Exception) {
                _state.value = UiState.Error(e.message ?: "initialization_failed")
            }
        }
    }

    fun navigate() = Unit
}
