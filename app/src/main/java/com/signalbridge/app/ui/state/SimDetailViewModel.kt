package com.signalbridge.app.ui.state

import com.signalbridge.app.app.SignalBridgeApplication
import com.signalbridge.app.domain.model.SimSubscription
import com.signalbridge.app.domain.model.ForwardingCondition
import com.signalbridge.app.domain.model.ObservedForwardingConfiguration

class SimDetailViewModel(app: SignalBridgeApplication) : ScreenViewModel(app) {

    data class ConditionRow(
        val condition: ForwardingCondition,
        val active: Boolean = false,
        val destination: String? = null,
        val status: String = "unknown" // verified | inactive | unavailable | unsupported
    )

    data class UiState(
        val subscription: SimSubscription? = null,
        val conditions: List<ConditionRow> = emptyList(),
        val isLoading: Boolean = false,
        val error: String? = null
    )

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    fun setSubscription(id: Int) {
        val sub = app.subscriptionRepository.currentSubscriptions().firstOrNull { it.subscriptionId == id }
        val rows = ForwardingCondition.values().map { ConditionRow(it, status = "unavailable") }
        _state.value = UiState(subscription = sub, conditions = rows)
    }

    fun refresh() {
        _state.value = _state.value.copy(isLoading = true)
        launch {
            val observed = app.callForwardingGateway.observe(
                _state.value.subscription?.subscriptionId ?: 0
            )
            val rows = ForwardingCondition.values().map { condition ->
                observed.ruleFor(condition)?.let { rule ->
                    ConditionRow(
                        condition = rule.condition,
                        active = rule.active,
                        destination = rule.destination,
                        status = if (rule.carrierConfirmed) "verified" else "unknown"
                    )
                } ?: ConditionRow(condition, status = "unavailable")
            }
            _state.value = _state.value.copy(isLoading = false, conditions = rows)
        }
    }
}
