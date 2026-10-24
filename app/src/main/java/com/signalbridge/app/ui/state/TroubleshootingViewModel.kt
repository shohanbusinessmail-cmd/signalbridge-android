package com.signalbridge.app.ui.state

import com.signalbridge.app.app.SignalBridgeApplication

class TroubleshootingViewModel(app: SignalBridgeApplication) : ScreenViewModel(app) {

    data class UiState(
        val errorCategory: String? = null,
        val message: String? = null,
        val retryEnabled: Boolean = true
    )

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    fun setError(category: String?, message: String?) {
        _state.value = UiState(errorCategory = category, message = message)
    }

    fun retry() {
        // Navigate back to the operation that failed; handled by the screen.
    }
}
