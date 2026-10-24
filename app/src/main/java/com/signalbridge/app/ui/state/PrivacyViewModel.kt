package com.signalbridge.app.ui.state

import com.signalbridge.app.app.SignalBridgeApplication

class PrivacyViewModel(app: SignalBridgeApplication) : ScreenViewModel(app) {

    data class UiState(
        val dataStoredLocally: Boolean = true,
        val analytics: Boolean = false,
        val cloudSync: Boolean = false
    )

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    fun clearLocalData() {
        // Deleting local profiles/history does NOT disable carrier forwarding.
        app.profileRepository // placeholder
        app.historyRepository // placeholder
    }
}
