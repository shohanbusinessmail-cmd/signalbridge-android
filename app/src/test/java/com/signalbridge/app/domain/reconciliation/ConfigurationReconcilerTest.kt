package com.signalbridge.app.domain.reconciliation

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import com.signalbridge.app.domain.model.ForwardingCondition
import com.signalbridge.app.domain.model.ForwardingRule
import com.signalbridge.app.domain.model.ObservedForwardingConfiguration
import com.signalbridge.app.domain.model.ObservedRule
import com.signalbridge.app.domain.model.RecoveryPolicy
import com.signalbridge.app.domain.model.SubscriptionForwardingState

class ConfigurationReconcilerTest {

    private fun rule(condition: ForwardingCondition, enabled: Boolean, destination: String = "+8801712345678"):
        ForwardingRule =
        ForwardingRule(subscriptionId = 1, condition = condition, destination = destination, enabled = enabled)

    private fun observed(condition: ForwardingCondition, active: Boolean, confirmed: Boolean = true,
                         destination: String? = "+8801712345678"): ObservedRule =
        ObservedRule(condition = condition, active = active, destination = destination,
                     timeoutSeconds = null, carrierConfirmed = confirmed)

    private fun observedConfig(vararg rules: ObservedRule): ObservedForwardingConfiguration =
        ObservedForwardingConfiguration(
            subscriptionId = 1,
            rules = rules.associateBy { it.condition },
            lastQueriedMs = System.currentTimeMillis(),
            querySource = "test"
        )

    @Test
    fun `in sync when desired matches observed`() {
        val desired = SubscriptionForwardingState(
            subscriptionId = 1,
            rules = mapOf(ForwardingCondition.UNREACHABLE to rule(ForwardingCondition.UNREACHABLE, true))
        )
        val observed = observedConfig(observed(ForwardingCondition.UNREACHABLE, true))
        val result = ConfigurationReconciler.reconcile(1, desired, observed, RecoveryPolicy.PRESERVE_NETWORK_SETTINGS)
        val unreachable = result.first { it.condition == ForwardingCondition.UNREACHABLE }
        assertEquals(ReconciliationDecision.IN_SYNC, unreachable.decision)
    }

    @Test
    fun `needs correction when desired differs from observed`() {
        val desired = SubscriptionForwardingState(
            subscriptionId = 1,
            rules = mapOf(ForwardingCondition.UNREACHABLE to rule(ForwardingCondition.UNREACHABLE, true))
        )
        val observed = observedConfig(observed(ForwardingCondition.UNREACHABLE, false))
        val result = ConfigurationReconciler.reconcile(1, desired, observed, RecoveryPolicy.PRESERVE_NETWORK_SETTINGS)
        val unreachable = result.first { it.condition == ForwardingCondition.UNREACHABLE }
        assertEquals(ReconciliationDecision.NEEDS_CORRECTION, unreachable.decision)
    }

    @Test
    fun `state unknown when not carrier confirmed`() {
        val desired = SubscriptionForwardingState(
            subscriptionId = 1,
            rules = mapOf(ForwardingCondition.UNREACHABLE to rule(ForwardingCondition.UNREACHABLE, true))
        )
        val observed = observedConfig(observed(ForwardingCondition.UNREACHABLE, true, confirmed = false))
        val result = ConfigurationReconciler.reconcile(1, desired, observed, RecoveryPolicy.PRESERVE_NETWORK_SETTINGS)
        val unreachable = result.first { it.condition == ForwardingCondition.UNREACHABLE }
        assertEquals(ReconciliationDecision.STATE_UNKNOWN, unreachable.decision)
    }

    @Test
    fun `preserve policy never forces correction automatically`() {
        val desired = SubscriptionForwardingState(
            subscriptionId = 1,
            rules = mapOf(ForwardingCondition.UNREACHABLE to rule(ForwardingCondition.UNREACHABLE, false))
        )
        val observed = observedConfig(observed(ForwardingCondition.UNREACHABLE, true))
        val result = ConfigurationReconciler.reconcile(1, desired, observed, RecoveryPolicy.PRESERVE_NETWORK_SETTINGS)
        val unreachable = result.first { it.condition == ForwardingCondition.UNREACHABLE }
        assertEquals(ReconciliationDecision.NEEDS_CORRECTION, unreachable.decision)
    }

    @Test
    fun `unknown observation yields state unknown`() {
        val desired = SubscriptionForwardingState(subscriptionId = 1, rules = emptyMap())
        val observed = observedConfig()
        val result = ConfigurationReconciler.reconcile(1, desired, observed, RecoveryPolicy.RECONCILE_WITH_SAVED_PROFILE)
        val unreachable = result.first { it.condition == ForwardingCondition.UNREACHABLE }
        assertEquals(ReconciliationDecision.STATE_UNKNOWN, unreachable.decision)
    }
}
