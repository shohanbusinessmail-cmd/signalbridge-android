package com.signalbridge.app.domain.model

/** A record of a forwarding operation for local history. */
data class ForwardingOperation(
    val id: String,
    val subscriptionId: Int,
    val condition: ForwardingCondition,
    val destination: String?,
    val resultType: String, // simple name of the ForwardingOperationResult subclass
    val resultSummaryKey: String,
    val timestampMs: Long,
    val carrierConfirmed: Boolean,
    val requiresUserAction: Boolean
)
