package com.signalbridge.app.ui.state

import com.signalbridge.app.app.SignalBridgeApplication

class SetupCompletionViewModel(app: SignalBridgeApplication) : ScreenViewModel(app) {

    data class UiState(
        val simsConfigured: Boolean = false,
        val carrierVerified: Boolean = false,
        val forwardingActive: Boolean = false,
        val pendingTask: String? = null,
        val allComplete: Boolean = false
    )

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    fun markComplete() {
        _state.value = _state.value.copy(allComplete = true)
    }
}
