package com.signalbridge.app.ui.state

import com.signalbridge.app.app.SignalBridgeApplication
import com.signalbridge.app.domain.model.ForwardingCondition

class ConditionDetailsViewModel(app: SignalBridgeApplication) : ScreenViewModel(app) {

    data class UiState(
        val condition: ForwardingCondition = ForwardingCondition.UNREACHABLE,
        val active: Boolean = false,
        val destination: String? = null,
        val isLoading: Boolean = false,
        val error: String? = null
    )

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    fun setCondition(condition: ForwardingCondition) {
        _state.value = _state.value.copy(condition = condition)
    }

    fun toggle() {
        _state.value = _state.value.copy(active = !_state.value.active)
    }
}
