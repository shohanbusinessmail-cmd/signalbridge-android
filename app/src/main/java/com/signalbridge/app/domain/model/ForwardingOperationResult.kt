package com.signalbridge.app.domain.model

/**
 * The result of a forwarding operation.
 *
 * Never collapse these into a Boolean: the UI must distinguish "the carrier
 * confirmed the configuration" from "the app merely submitted a request" from
 * "the state could not be verified."
 */
sealed interface ForwardingOperationResult {
    val operationId: String
    val condition: ForwardingCondition
    val timestampMs: Long
}

/** The carrier confirmed the configuration matches the request. */
data class SuccessConfirmed(
    override val operationId: String,
    override val condition: ForwardingCondition,
    override val timestampMs: Long,
    val confirmedDestination: String?,
    val confirmationSource: String // e.g. "network-query", "carrier-ussd"
) : ForwardingOperationResult

/** The app initiated the action but has no carrier confirmation yet. */
data class SuccessUnverified(
    override val operationId: String,
    override val condition: ForwardingCondition,
    override val timestampMs: Long,
    val submittedVia: String // e.g. "mmi-dialer", "telephony-api"
) : ForwardingOperationResult

/** The user must complete an action (e.g. confirm in the system dialer). */
data class PendingUserAction(
    override val operationId: String,
    override val condition: ForwardingCondition,
    override val timestampMs: Long,
    val requiredAction: String,
    val instructionKey: String
) : ForwardingOperationResult

/** The platform or carrier does not support this operation. */
object Unsupported : ForwardingOperationResult {
    override val operationId: String = ""
    override val condition: ForwardingCondition = ForwardingCondition.UNCONDITIONAL
    override val timestampMs: Long = 0L
}

/** A required permission is denied or not grantable to a third-party app. */
data class PermissionDenied(
    override val operationId: String,
    override val condition: ForwardingCondition,
    override val timestampMs: Long,
    val permission: String,
    val canOpenSettings: Boolean
) : ForwardingOperationResult

/** The carrier rejected the request. */
data class CarrierRejected(
    override val operationId: String,
    override val condition: ForwardingCondition,
    override val timestampMs: Long,
    val reasonKey: String,
    val retryable: Boolean
) : ForwardingOperationResult

/** The network is currently unavailable (roaming, out of coverage, etc.). */
object NetworkUnavailable : ForwardingOperationResult {
    override val operationId: String = ""
    override val condition: ForwardingCondition = ForwardingCondition.UNCONDITIONAL
    override val timestampMs: Long = 0L
}

/** The target subscription is unavailable (SIM removed, disabled, etc.). */
data class SubscriptionUnavailable(
    override val operationId: String,
    override val condition: ForwardingCondition,
    override val timestampMs: Long,
    val subscriptionId: Int
) : ForwardingOperationResult

/** The destination number is invalid or not reachable to validate. */
data class InvalidNumber(
    override val operationId: String,
    override val condition: ForwardingCondition,
    override val timestampMs: Long,
    val number: String,
    val reasonKey: String
) : ForwardingOperationResult

/** The operation took too long and was abandoned. */
object OperationTimedOut : ForwardingOperationResult {
    override val operationId: String = ""
    override val condition: ForwardingCondition = ForwardingCondition.UNCONDITIONAL
    override val timestampMs: Long = 0L
}

/** The network state could not be queried; treat as unknown, not disabled. */
object StateUnknown : ForwardingOperationResult {
    override val operationId: String = ""
    override val condition: ForwardingCondition = ForwardingCondition.UNCONDITIONAL
    override val timestampMs: Long = 0L
}

/** The user cancelled the operation. */
object UserCancelled : ForwardingOperationResult {
    override val operationId: String = ""
    override val condition: ForwardingCondition = ForwardingCondition.UNCONDITIONAL
    override val timestampMs: Long = 0L
}

/** Confirmation is required from the carrier/user before the state is final. */
data class ConfirmationRequired(
    override val operationId: String,
    override val condition: ForwardingCondition,
    override val timestampMs: Long,
    val confirmationKey: String
) : ForwardingOperationResult

/** An unexpected application or platform failure. */
data class UnexpectedFailure(
    override val operationId: String,
    override val condition: ForwardingCondition,
    override val timestampMs: Long,
    val exception: Throwable?,
    val messageKey: String
) : ForwardingOperationResult

/** Helper: whether the result represents a final, carrier-confirmed success. */
fun ForwardingOperationResult.isConfirmed(): Boolean = this is SuccessConfirmed

/** Helper: whether the result is terminal (no further action expected without user input). */
fun ForwardingOperationResult.isTerminalFailure(): Boolean =
    this is Unsupported || this is PermissionDenied || this is CarrierRejected ||
    this is NetworkUnavailable || this is SubscriptionUnavailable || this is InvalidNumber ||
    this is OperationTimedOut || this is UnexpectedFailure
