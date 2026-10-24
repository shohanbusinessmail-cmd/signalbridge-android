package com.signalbridge.app.ui.state

import com.signalbridge.app.app.SignalBridgeApplication
import com.signalbridge.app.domain.model.ForwardingOperation

class HistoryViewModel(app: SignalBridgeApplication) : ScreenViewModel(app) {

    data class UiState(
        val records: List<ForwardingOperation> = emptyList(),
        val isLoading: Boolean = false
    )

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    fun load() {
        _state.value = _state.value.copy(isLoading = true)
        launch {
            val records = app.historyRepository.records()
            _state.value = UiState(records = records, isLoading = false)
        }
    }

    fun clear() {
        app.historyRepository.clear()
        load()
    }
}
