package com.signalbridge.app.domain.reconciliation

import com.signalbridge.app.domain.model.ForwardingCondition
import com.signalbridge.app.domain.model.ForwardingRule
import com.signalbridge.app.domain.model.ObservedForwardingConfiguration
import com.signalbridge.app.domain.model.ObservedRule
import com.signalbridge.app.domain.model.SubscriptionForwardingState
import com.signalbridge.app.domain.model.RecoveryPolicy

/** The outcome of comparing desired vs observed configuration. */
enum class ReconciliationDecision {
    /** Observed already matches desired; nothing to do. */
    IN_SYNC,
    /** Desired differs from observed; offer/perform correction. */
    NEEDS_CORRECTION,
    /** Observed state could not be verified; do not correct automatically. */
    STATE_UNKNOWN,
    /** Carrier does not support querying this condition. */
    UNSUPPORTED
}

data class ReconciliationResult(
    val subscriptionId: Int,
    val condition: ForwardingCondition,
    val decision: ReconciliationDecision,
    val desired: ForwardingRule?,
    val observed: ObservedRule?,
    val explanationKey: String
)

/**
 * Compares a saved desired profile against the carrier-observed configuration.
 * Never mutates carrier state on its own.
 */
object ConfigurationReconciler {

    fun reconcile(
        subscriptionId: Int,
        desired: SubscriptionForwardingState,
        observed: ObservedForwardingConfiguration,
        policy: RecoveryPolicy
    ): List<ReconciliationResult> {
        return ForwardingCondition.values().map { condition ->
            val desiredRule = desired.ruleFor(condition)
            val observedRule = observed.ruleFor(condition)
            reconcileOne(condition, desiredRule, observedRule, policy)
        }
    }

    private fun reconcileOne(
        condition: ForwardingCondition,
        desired: ForwardingRule?,
        observed: ObservedRule?,
        policy: RecoveryPolicy
    ): ReconciliationResult {
        val explanationKey = when {
            observed == null -> "reconcile_unverified"
            policy == RecoveryPolicy.PRESERVE_NETWORK_SETTINGS -> "reconcile_preserve"
            desired == null && observed != null && observed.active -> "reconcile_leave_active"
            else -> "reconcile_default"
        }

        val decision = when {
            observed == null -> ReconciliationDecision.STATE_UNKNOWN
            !observed.carrierConfirmed -> ReconciliationDecision.STATE_UNKNOWN
            policy == RecoveryPolicy.PRESERVE_NETWORK_SETTINGS -> {
                // Never change carrier state automatically under this policy.
                if (desired != null && desired.enabled != observed.active) {
                    ReconciliationDecision.NEEDS_CORRECTION // only offered, never forced
                } else {
                    ReconciliationDecision.IN_SYNC
                }
            }
            desired == null -> ReconciliationDecision.IN_SYNC
            desired.enabled == observed.active &&
                desired.destination == observed.destination ->
                ReconciliationDecision.IN_SYNC
            else -> ReconciliationDecision.NEEDS_CORRECTION
        }

        return ReconciliationResult(
            subscriptionId = subscriptionId,
            condition = condition,
            decision = decision,
            desired = desired,
            observed = observed,
            explanationKey = explanationKey
        )
    }
}
