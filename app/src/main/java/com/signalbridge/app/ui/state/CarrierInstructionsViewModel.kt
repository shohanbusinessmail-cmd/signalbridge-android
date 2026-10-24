package com.signalbridge.app.ui.state

import com.signalbridge.app.app.SignalBridgeApplication

class CarrierInstructionsViewModel(app: SignalBridgeApplication) : ScreenViewModel(app) {

    data class UiState(
        val carrier: String = "Unknown carrier",
        val supportedConditions: List<String> = emptyList(),
        val instructions: String = "",
        val charges: String = "",
        val isUnverified: Boolean = true
    )

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    fun setCarrier(carrier: String) {
        _state.value = _state.value.copy(carrier = carrier)
    }
}
