package com.signalbridge.app.domain.model

/**
 * Structured carrier compatibility record. Capabilities live in this data model
 * rather than as scattered conditionals throughout the UI.
 *
 * Every capability starts at UNKNOWN and is promoted only when backed by
 * evidence (see docs/TECHNICAL_RESEARCH.md).
 */
enum class CapabilityState {
    SUPPORTED, UNSUPPORTED, UNKNOWN
}

enum class ConfirmationRequirement {
    NONE, REQUIRED_BY_CARRIER, REQUIRED_BY_PLATFORM, UNKNOWN
}

data class CarrierCapability(
    val carrierName: String,
    val countryIso: String,
    val condition: ForwardingCondition,
    val queryCapability: CapabilityState = CapabilityState.UNKNOWN,
    val activationCapability: CapabilityState = CapabilityState.UNKNOWN,
    val deactivationCapability: CapabilityState = CapabilityState.UNKNOWN,
    val requiredPermissions: List<String> = emptyList(),
    val userConfirmation: ConfirmationRequirement = ConfirmationRequirement.UNKNOWN,
    val verificationMechanism: String? = null,
    val knownRestrictions: String? = null,
    val evidenceSource: String? = null,
    val dateLastChecked: String? = null,
    val confidence: String = "none", // none | low | medium | high
    val manualFallbackInstructionsKey: String? = null
)

data class CarrierRegistry(
    val entries: List<CarrierCapability> = emptyList()
) {
    fun capabilityFor(carrierName: String, countryIso: String, condition: ForwardingCondition):
        CarrierCapability? =
        entries.firstOrNull {
            it.caraterEqualsIgnoreCase(carrierName) &&
            it.countryIso.equals(countryIso, ignoreCase = true) &&
            it.condition == condition
        }

    // Private helper kept inline to avoid an extra file.
    private fun String.caraterEqualsIgnoreCase(other: String?) =
        other != null && this.equals(other, ignoreCase = true)
}
