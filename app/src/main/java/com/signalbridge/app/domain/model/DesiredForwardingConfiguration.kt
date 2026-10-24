package com.signalbridge.app.domain.model

/**
 * The complete desired forwarding configuration produced by the setup wizard or
 * profile editor, ready to be applied through the CallForwardingGateway.
 */
data class DesiredForwardingConfiguration(
    val subscriptions: List<Int>, // selected subscription ids
    val destination: String,
    val condition: ForwardingCondition,
    val timeoutSeconds: Int? = null,
    val recoveryPolicy: RecoveryPolicy = RecoveryPolicy.PreserveNetworkSettings,
    /** Different destinations per subscription (overrides the shared destination). */
    val perSubscriptionDestination: Map<Int, String> = emptyMap(),
    val notes: String? = null
) {
    fun destinationFor(subscriptionId: Int): String =
        perSubscriptionDestination[subscriptionId] ?: destination

    fun isValid(): Boolean = subscriptions.isNotEmpty() && destinationFor(subscriptions.first()).isNotBlank()
}
