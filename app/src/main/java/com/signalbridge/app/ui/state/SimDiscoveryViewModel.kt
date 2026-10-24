package com.signalbridge.app.ui.state

import com.signalbridge.app.app.SignalBridgeApplication
import com.signalbridge.app.domain.model.SimSubscription

class SimDiscoveryViewModel(app: SignalBridgeApplication) : ScreenViewModel(app) {

    data class UiState(
        val subscriptions: List<SimSubscription> = emptyList(),
        val isLoading: Boolean = false,
        val error: String? = null,
        val empty: Boolean = false
    )

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        refresh()
    }

    fun refresh() {
        _state.value = _state.value.copy(isLoading = true, error = null)
        launch {
            val subs = app.subscriptionRepository.refresh()
            _state.value = UiState(
                subscriptions = subs,
                isLoading = false,
                empty = subs.isEmpty()
            )
        }
    }
}
