package com.signalbridge.app.domain.model

/**
 * A user-saved desired configuration. A profile is a *desired* state, not proof
 * that the carrier has applied it.
 */
data class ForwardingProfile(
    val id: String,
    val name: String,
    val description: String?,
    /** Per-subscription desired state keyed by subscription id. */
    val subscriptions: Map<Int, SubscriptionForwardingState>,
    val recoveryPolicy: RecoveryPolicy,
    val isDefault: Boolean = false,
    val createdAtMs: Long = System.currentTimeMillis(),
    val updatedAtMs: Long = System.currentTimeMillis(),
    val notes: String? = null
) {
    fun subscriptionState(subscriptionId: Int): SubscriptionForwardingState? =
        subscriptions[subscriptionId]
}
