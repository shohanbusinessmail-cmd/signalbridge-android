package com.signalbridge.app.ui.state

import com.signalbridge.app.app.SignalBridgeApplication
import com.signalbridge.app.domain.model.SimSubscription

class SimSelectionViewModel(app: SignalBridgeApplication) : ScreenViewModel(app) {

    data class UiState(
        val subscriptions: List<SimSubscription> = emptyList(),
        val selectedIds: List<Int> = emptyList(),
        val isLoading: Boolean = false
    )

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    fun setSubscriptions(subscriptions: List<SimSubscription>) {
        _state.value = _state.value.copy(subscriptions = subscriptions)
    }

    fun toggle(subscriptionId: Int) {
        val current = _state.value.selectedIds
        val next = if (current.contains(subscriptionId)) {
            current.filter { it != subscriptionId }
        } else {
            current + subscriptionId
        }
        _state.value = _state.value.copy(selectedIds = next)
    }

    fun clearSelection() {
        _state.value = _state.value.copy(selectedIds = emptyList())
    }
}
