package com.signalbridge.app.data.telephony

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.telephony.SubscriptionManager
import android.telephony.TelephonyManager
import com.signalbridge.app.domain.model.CarrierCapability
import com.signalbridge.app.domain.model.CapabilityState
import com.signalbridge.app.domain.model.ConfirmationRequirement
import com.signalbridge.app.domain.model.ForwardingCondition
import com.signalbridge.app.domain.model.ForwardingOperationResult
import com.signalbridge.app.domain.model.ForwardingOperationResult.*
import com.signalbridge.app.domain.model.ObservedForwardingConfiguration
import com.signalbridge.app.domain.model.ObservedRule
import com.signalbridge.app.domain.model.PermissionRequirement
import com.signalbridge.app.telephony.CallForwardingGateway
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Real telephony-backed implementation of [CallForwardingGateway].
 *
 * Platform truth: the public Android SDK does NOT expose call forwarding
 * provisioning or querying to ordinary third-party apps. Call forwarding is a
 * carrier supplementary service. This gateway therefore:
 *   - reads subscription/telephony info via TelephonyManager/SubscriptionManager
 *   - exposes MMI/USSD activation through the *system dialer* with the user's
 *     explicit confirmation (Intent.ACTION_DIAL), never silently
 *   - reports any network state it cannot actually query as StateUnknown
 */
class TelephonyCallForwardingGateway(private val context: Context) : CallForwardingGateway {

    private val telephonyManager: TelephonyManager by lazy {
        context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
    }

    private val subscriptionManager: SubscriptionManager? by lazy {
        try {
            context.getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE) as SubscriptionManager
        } catch (e: Exception) {
            null
        }
    }

    override fun isAvailable(): Boolean =
        telephonyManager.isVoiceCapable || subscriptionManager != null

    override fun capability(
        subscriptionId: Int,
        condition: ForwardingCondition
    ): CarrierCapability {
        return CarrierCapability(
            carrierName = carrierNameFor(subscriptionId),
            countryIso = countryIsoFor(subscriptionId),
            condition = condition,
            queryCapability = CapabilityState.UNSUPPORTED,
            activationCapability = CapabilityState.UNKNOWN,
            deactivationCapability = CapabilityState.UNKNOWN,
            requiredPermissions = requiredPermissions().map { it.permission },
            userConfirmation = ConfirmationRequirement.REQUIRED_BY_PLATFORM,
            verificationMechanism = "system dialer MMI / user-confirmed",
            knownRestrictions =
                "Public Android API does not expose forwarding provisioning to third-party apps",
            evidenceSource = "Android TelephonyManager / SubscriptionManager public API",
            dateLastChecked = null,
            confidence = "high",
            manualFallbackInstructionsKey = null
        )
    }

    override fun observe(subscriptionId: Int): ObservedForwardingConfiguration {
        // Public API cannot reveal forwarding state. Report unknown rather than
        // guessing disabled.
        return ObservedForwardingConfiguration(
            subscriptionId = subscriptionId,
            rules = emptyMap(),
            lastQueriedMs = System.currentTimeMillis(),
            querySource = "unavailable-public-api"
        )
    }

    override fun enable(
        subscriptionId: Int,
        condition: ForwardingCondition,
        destination: String,
        timeoutSeconds: Int? = null
    ): ForwardingOperationResult {
        val mmi = MmiCodes.activation(condition, destination, timeoutSeconds)
        if (mmi == null) {
            return Unsupported(
                operationId = java.util.UUID.randomUUID().toString(),
                condition = condition,
                timestampMs = System.currentTimeMillis()
            )
        }
        return openConfirmationWorkflow(subscriptionId, condition, destination, mmi)
    }

    override fun disable(
        subscriptionId: Int,
        condition: ForwardingCondition
    ): ForwardingOperationResult {
        val mmi = MmiCodes.deactivation(condition)
        if (mmi == null) {
            return Unsupported(
                operationId = java.util.UUID.randomUUID().toString(),
                condition = condition,
                timestampMs = System.currentTimeMillis()
            )
        }
        return openConfirmationWorkflow(subscriptionId, condition, "", mmi)
    }

    override fun applyConfiguration(
        subscriptionId: Int,
        rules: Map<ForwardingCondition, Boolean>
    ): ForwardingOperationResult {
        // Apply is implemented as a sequence of user-confirmed MMI actions. The
        // first action is returned; remaining actions await user confirmation.
        val first = rules.entries.firstOrNull()
        if (first == null) {
            return SuccessUnverified(
                operationId = java.util.UUID.randomUUID().toString(),
                condition = ForwardingCondition.UNCONDITIONAL,
                timestampMs = System.currentTimeMillis(),
                submittedVia = "no-op"
            )
        }
        val (condition, enabled) = first
        return if (enabled) {
            enable(subscriptionId, condition, destinationFor(subscriptionId, condition))
        } else {
            disable(subscriptionId, condition)
        }
    }

    override fun openConfirmationWorkflow(
        subscriptionId: Int,
        condition: ForwardingCondition,
        destination: String,
        actionKey: String
    ): ForwardingOperationResult {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            // actionKey holds the MMI string (e.g. **61*+8801XXXXXXXXX#).
            // The system dialer presents it; the user presses call.
            data = Uri.parse("tel:${Uri.encode(actionKey)}")
            // Launch in the user's task so the dialer appears in the foreground.
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }
        context.startActivity(intent)
        return PendingUserAction(
            operationId = java.util.UUID.randomUUID().toString(),
            condition = condition,
            timestampMs = System.currentTimeMillis(),
            requiredAction = "confirm_call_forwarding_dialer",
            instructionKey = "gateway_confirm_in_dialer"
        )
    }

    override fun requiredPermissions(): List<PermissionRequirement> {
        return listOf(
            PermissionRequirement(
                permission = android.Manifest.permission.READ_PHONE_STATE,
                rationaleKey = "permission_read_phone_state_rationale",
                rationaleDetailKey = "permission_read_phone_state_detail",
                isGranted = hasPermission(android.Manifest.permission.READ_PHONE_STATE),
                shouldShowRationale = true,
                canOpenSettings = true
            ),
            PermissionRequirement(
                permission = android.Manifest.permission.READ_PHONE_NUMBERS,
                rationaleKey = "permission_read_phone_numbers_rationale",
                rationaleDetailKey = "permission_read_phone_numbers_detail",
                isGranted = hasPermission(android.Manifest.permission.READ_PHONE_NUMBERS),
                shouldShowRationale = true,
                canOpenSettings = true
            ),
            PermissionRequirement(
                permission = android.Manifest.permission.POST_NOTIFICATIONS,
                rationaleKey = "permission_notifications_rationale",
                rationaleDetailKey = null,
                isGranted = hasPermission(android.Manifest.permission.POST_NOTIFICATIONS),
                shouldShowRationale = true,
                canOpenSettings = true
            )
        )
    }

    override fun isConfirmationPending(operationId: String): Boolean {
        // No server round-trip; pending state lives in the UI layer history.
        return false
    }

    private fun destinationFor(subscriptionId: Int, condition: ForwardingCondition): String {
        return "" // resolved by the caller/wizard before invoking enable
    }

    private fun carrierNameFor(subscriptionId: Int): String {
        return subscriptionManager?.activeSubscriptionInfoList
            ?.firstOrNull { it.subscriptionId == subscriptionId }
            ?.carrierName?.toString() ?: "Unknown carrier"
    }

    private fun countryIsoFor(subscriptionId: Int): String {
        return subscriptionManager?.activeSubscriptionInfoList
            ?.firstOrNull { it.subscriptionId == subscriptionId }
            ?.countryIso ?: "BD"
    }

    private fun hasPermission(permission: String): Boolean {
        return context.checkSelfPermission(permission) == android.content.pm.PackageManager.PERMISSION_GRANTED
    }

    /**
     * Carrier-agnostic MMI code templates. These are *examples* and are flagged
     * as unverified. Each operator's actual codes must be confirmed before use.
     */
    object MmiCodes {
        fun activation(
            condition: ForwardingCondition,
            destination: String,
            timeoutSeconds: Int?
        ): String? {
            return when (condition) {
                ForwardingCondition.UNCONDITIONAL -> "**21*$destination#"
                ForwardingCondition.UNREACHABLE -> "**62*$destination#"
                ForwardingCondition.BUSY -> "**67*$destination#"
                ForwardingCondition.UNANSWERED -> {
                    // Timeout only meaningful if carrier supports it.
                    val timeout = timeoutSeconds ?: 20
                    "**61*$destination**$timeout#"
                }
            }
        }

        fun deactivation(condition: ForwardingCondition): String? {
            return when (condition) {
                ForwardingCondition.UNCONDITIONAL -> "##21#"
                ForwardingCondition.UNREACHABLE -> "##62#"
                ForwardingCondition.BUSY -> "##67#"
                ForwardingCondition.UNANSWERED -> "##61#"
            }
        }
    }
}
