package com.signalbridge.app.ui.state

import com.signalbridge.app.app.SignalBridgeApplication
import com.signalbridge.app.domain.model.DesiredForwardingConfiguration
import com.signalbridge.app.domain.model.ForwardingCondition
import com.signalbridge.app.domain.model.RecoveryPolicy
import com.signalbridge.app.domain.model.ForwardingOperationResult

class ForwardingWizardViewModel(app: SignalBridgeApplication) : ScreenViewModel(app) {

    enum class Step {
        SELECT_SIM, CHOOSE_DESTINATION, CHOOSE_CONDITION, CHOOSE_POLICY,
        CAPABILITY_CHECK, REVIEW, APPLY, COMPLETE
    }

    data class UiState(
        val step: Step = Step.SELECT_SIM,
        val config: DesiredForwardingConfiguration =
            DesiredForwardingConfiguration(
                subscriptions = emptyList(),
                destination = "",
                condition = ForwardingCondition.UNREACHABLE
            ),
        val isApplying: Boolean = false,
        val result: ForwardingOperationResult? = null,
        val error: String? = null
    )

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    fun setSelectedSubscriptions(ids: List<Int>) {
        _state.value = _state.value.copy(config = _state.value.config.copy(subscriptions = ids))
    }

    fun setDestination(destination: String) {
        _state.value = _state.value.copy(config = _state.value.config.copy(destination = destination))
    }

    fun setCondition(condition: ForwardingCondition) {
        _state.value = _state.value.copy(config = _state.value.config.copy(condition = condition))
    }

    fun setRecoveryPolicy(policy: RecoveryPolicy) {
        _state.value = _state.value.copy(config = _state.value.config.copy(recoveryPolicy = policy))
    }

    fun nextStep() {
        val current = _state.value.step
        _state.value = _state.value.copy(step = when (current) {
            Step.SELECT_SIM -> Step.CHOOSE_DESTINATION
            Step.CHOOSE_DESTINATION -> Step.CHOOSE_CONDITION
            Step.CHOOSE_CONDITION -> Step.CHOOSE_POLICY
            Step.CHOOSE_POLICY -> Step.CAPABILITY_CHECK
            Step.CAPABILITY_CHECK -> Step.REVIEW
            Step.REVIEW -> Step.APPLY
            else -> current
        })
    }

    fun previousStep() {
        val current = _state.value.step
        _state.value = _state.value.copy(step = when (current) {
            Step.CHOOSE_DESTINATION -> Step.SELECT_SIM
            Step.CHOOSE_CONDITION -> Step.CHOOSE_DESTINATION
            Step.CHOOSE_POLICY -> Step.CHOOSE_CONDITION
            Step.CAPABILITY_CHECK -> Step.CHOOSE_POLICY
            Step.REVIEW -> Step.CAPABILITY_CHECK
            Step.APPLY -> Step.REVIEW
            else -> current
        })
    }

    fun apply() {
        _state.value = _state.value.copy(isApplying = true, result = null)
        launch {
            val config = _state.value.config
            val firstSub = config.subscriptions.firstOrNull() ?: return@launch
            val result = app.callForwardingGateway.enable(
                subscriptionId = firstSub,
                condition = config.condition,
                destination = config.destinationFor(firstSub)
            )
            _state.value = _state.value.copy(isApplying = false, result = result)
        }
    }
}
