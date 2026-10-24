package com.signalbridge.app.domain.model

/**
 * The carrier-observed (actual) forwarding state for one subscription, captured
 * at a point in time. May be incomplete if the carrier does not expose a rule.
 */
data class ObservedForwardingConfiguration(
    val subscriptionId: Int,
    val rules: Map<ForwardingCondition, ObservedRule>,
    val lastQueriedMs: Long,
    val querySource: String // e.g. "telephony-api", "mmi-query", "unavailable"
) {
    fun ruleFor(condition: ForwardingCondition): ObservedRule? = rules[condition]
    fun isAnyActive(): Boolean = rules.values.any { it.active }
}

data class ObservedRule(
    val condition: ForwardingCondition,
    val active: Boolean,
    val destination: String?,
    val timeoutSeconds: Int?,
    val carrierConfirmed: Boolean
)
