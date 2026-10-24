package com.signalbridge.app.domain.model

/**
 * A discovered telephony subscription (SIM / eSIM profile).
 *
 * Identity is based on the subscription id and a stable subscription info
 * record; slot index is intentionally NOT used as a stable identity because
 * slot numbering can change when a SIM is moved between slots.
 */
data class SimSubscription(
    val subscriptionId: Int,
    val simSlotIndex: Int?,
    val carrierName: String?,
    val countryIso: String?,
    val number: String?, // masked before display/logging
    val isActive: Boolean,
    val isRoaming: Boolean,
    val dataProfile: Boolean,
    val subscriptionInfoHash: String? // stable hash of underlying SubscriptionInfo
) {
    fun displayName(): String = carrierName ?: "Unknown carrier"

    fun displayLabel(): String {
        val slot = simSlotIndex?.let { "SIM ${simSlotIndex + 1}" } ?: "SIM"
        return carrierName?.let { "$slot - $it" } ?: slot
    }

    /** Returns a privacy-safe masked representation of the number for UI/display. */
    fun maskedNumber(): String {
        return number.takeIf { it.isNotBlank() }?.run {
            val digits = filter { it.isDigit() }
            return if (digits.length <= 4) digits else {
                val tail = digits.takeLast(4)
                "****" + tail
            }
        } ?: "Number unavailable"
    }
}
