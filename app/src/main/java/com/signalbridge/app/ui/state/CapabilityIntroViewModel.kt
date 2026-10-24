package com.signalbridge.app.ui.state

import com.signalbridge.app.app.SignalBridgeApplication

class CapabilityIntroViewModel(app: SignalBridgeApplication) : ScreenViewModel(app) {

    data class UiState(
        val chargesAcknowledged: Boolean = false
    )

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    fun toggleChargesAcknowledged(checked: Boolean) {
        _state.value = _state.value.copy(chargesAcknowledged = checked)
    }
}
