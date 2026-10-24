package com.signalbridge.app.ui.state

import com.signalbridge.app.app.SignalBridgeApplication
import com.signalbridge.app.domain.model.ForwardingCondition
import com.signalbridge.app.domain.model.ForwardingOperationResult

class QuickControlViewModel(app: SignalBridgeApplication) : ScreenViewModel(app) {

    data class UiState(
        val condition: ForwardingCondition = ForwardingCondition.UNREACHABLE,
        val destination: String = "",
        val isActive: Boolean = false,
        val isOperating: Boolean = false,
        val result: ForwardingOperationResult? = null,
        val error: String? = null
    )

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    fun setCondition(condition: ForwardingCondition) {
        _state.value = _state.value.copy(condition = condition, result = null)
    }

    fun setDestination(destination: String) {
        _state.value = _state.value.copy(destination = destination, result = null)
    }

    fun toggle() {
        if (_state.value.isOperating) return // prevent duplicate operations
        _state.value = _state.value.copy(isOperating = true, result = null)
        launch {
            val subId = selectedSubscriptionId()
            val result = if (_state.value.isActive) {
                app.callForwardingGateway.disable(subId, _state.value.condition)
            } else {
                app.callForwardingGateway.enable(subId, _state.value.condition, _state.value.destination)
            }
            _state.value = _state.value.copy(
                isOperating = false,
                result = result,
                isActive = if (result is ForwardingOperationResult.SuccessConfirmed) true else _state.value.isActive
            )
        }
    }

    private fun selectedSubscriptionId(): Int = 0 // resolved from selected SIM context
}
