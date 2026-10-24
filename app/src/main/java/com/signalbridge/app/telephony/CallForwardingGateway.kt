package com.signalbridge.app.telephony

import com.signalbridge.app.domain.model.CarrierCapability
import com.signalbridge.app.domain.model.ForwardingCondition
import com.signalbridge.app.domain.model.ForwardingOperationResult
import com.signalbridge.app.domain.model.ObservedForwardingConfiguration
import com.signalbridge.app.domain.model.PermissionRequirement

/**
 * Platform-independent abstraction for call forwarding.
 *
 * All operations are asynchronous and return a rich result. The gateway never
 * pretends success: the result tells the UI whether the carrier confirmed the
 * configuration or the app merely initiated an action.
 */
interface CallForwardingGateway {

    /** Whether this gateway (and platform) can handle forwarding at all. */
    fun isAvailable(): Boolean

    /** Capability for a given subscription and condition. */
    fun capability(subscriptionId: Int, condition: ForwardingCondition): CarrierCapability

    /** Query the current observed configuration for a subscription. */
    fun observe(subscriptionId: Int): ObservedForwardingConfiguration

    /** Enable a forwarding condition for a subscription. */
    fun enable(
        subscriptionId: Int,
        condition: ForwardingCondition,
        destination: String,
        timeoutSeconds: Int? = null
    ): ForwardingOperationResult

    /** Disable a forwarding condition for a subscription. */
    fun disable(
        subscriptionId: Int,
        condition: ForwardingCondition
    ): ForwardingOperationResult

    /** Apply a complete desired configuration (may span conditions/subscriptions). */
    fun applyConfiguration(subscriptionId: Int, rules: Map<ForwardingCondition, Boolean>): ForwardingOperationResult

    /** Open a user-confirmed system/carrier workflow (e.g. system dialer MMI). */
    fun openConfirmationWorkflow(
        subscriptionId: Int,
        condition: ForwardingCondition,
        destination: String,
        actionKey: String
    ): ForwardingOperationResult

    /** Permissions required by this gateway. */
    fun requiredPermissions(): List<PermissionRequirement>

    /** Whether a previously initiated operation is still awaiting confirmation. */
    fun isConfirmationPending(operationId: String): Boolean
}
