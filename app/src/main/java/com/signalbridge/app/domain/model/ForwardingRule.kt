package com.signalbridge.app.domain.model

/**
 * A single forwarding rule: one condition -> one destination on one subscription.
 */
data class ForwardingRule(
    val subscriptionId: Int,
    val condition: ForwardingCondition,
    val destination: String,
    val timeoutSeconds: Int? = null, // meaningful only for UNANSWERED
    val enabled: Boolean = false,
    val carrierConfirmed: Boolean = false,
    val lastCheckedMs: Long = 0L
) {
    fun isValid(): Boolean = destination.isNotBlank() && destination.contains(Regex(Regex.escape("+")))
        || destination.isNotBlank()
}

/** A complete desired state for one subscription: multiple conditions. */
data class SubscriptionForwardingState(
    val subscriptionId: Int,
    val rules: Map<ForwardingCondition, ForwardingRule>
) {
    fun ruleFor(condition: ForwardingCondition): ForwardingRule? = rules[condition]
    fun enabledConditions(): List<ForwardingCondition> =
        rules.values.filter { it.enabled }.map { it.condition }
}
