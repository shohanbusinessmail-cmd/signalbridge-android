package com.signalbridge.app.ui.state

import com.signalbridge.app.app.SignalBridgeApplication
import com.signalbridge.app.domain.validation.NumberValidator

class DestinationPickerViewModel(app: SignalBridgeApplication) : ScreenViewModel(app) {

    data class UiState(
        val number: String = "",
        val normalized: String? = null,
        val error: String? = null,
        val isValid: Boolean = false,
        val savedDestinations: List<String> = emptyList()
    )

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    fun onNumberChanged(raw: String) {
        _state.value = _state.value.copy(number = raw, error = null)
        val (normalized, invalid) = NumberValidator.validateRaw(raw)
        _state.value = _state.value.copy(
            normalized = normalized,
            error = if (invalid != null) invalid.reasonKey else null,
            isValid = invalid == null && normalized != null
        )
    }

    fun selectSaved(destination: String) {
        _state.value = _state.value.copy(number = destination, error = null)
    }
}
