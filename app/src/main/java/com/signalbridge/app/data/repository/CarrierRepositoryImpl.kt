package com.signalbridge.app.data.repository

import com.signalbridge.app.domain.model.CarrierCapability
import com.signalbridge.app.domain.model.CapabilityState
import com.signalbridge.app.domain.model.CarrierRepository
import com.signalbridge.app.domain.model.CarrierRegistry
import com.signalbridge.app.domain.model.ConfirmationRequirement
import com.signalbridge.app.domain.model.ForwardingCondition

class CarrierRepositoryImpl : CarrierRepository {

    override fun registry(): CarrierRegistry {
        return CarrierRegistry(entries = BANGLADESH_OPERATORS)
    }

    /**
     * Initial entries for Bangladesh's major operators.
     *
     * Every capability is UNKNOWN until backed by evidence recorded in
     * docs/TECHNICAL_RESEARCH.md. Do not promote a capability merely because the
     * operator offers call forwarding in general.
     */
    private val BANGLADESH_OPERATORS: List<CarrierCapability> = listOf(
        // Grameenphone
        CarrierCapability(
            carrierName = "Grameenphone", countryIso = "BD", condition = ForwardingCondition.UNREACHABLE,
            userConfirmation = ConfirmationRequirement.REQUIRED_BY_PLATFORM,
            verificationMechanism = "network-query or user-confirmed MMI",
            knownRestrictions = "MMI code and confirmation behavior not yet evidenced",
            evidenceSource = null, dateLastChecked = null, confidence = "none",
            manualFallbackInstructionsKey = "carrier_instructions_placeholder"
        ),
        CarrierCapability(
            carrierName = "Grameenphone", countryIso = "BD", condition = ForwardingCondition.BUSY,
            userConfirmation = ConfirmationRequirement.UNKNOWN,
            verificationMechanism = null,
            knownRestrictions = "Not evidenced",
            evidenceSource = null, dateLastChecked = null, confidence = "none",
            manualFallbackInstructionsKey = null
        ),
        CarrierCapability(
            carrierName = "Grameenphone", countryIso = "BD", condition = ForwardingCondition.UNANSWERED,
            userConfirmation = ConfirmationRequirement.UNKNOWN,
            verificationMechanism = null,
            knownRestrictions = "Timeout configurability not evidenced",
            evidenceSource = null, dateLastChecked = null, confidence = "none",
            manualFallbackInstructionsKey = null
        ),
        CarrierCapability(
            carrierName = "Grameenphone", countryIso = "BD", condition = ForwardingCondition.UNCONDITIONAL,
            userConfirmation = ConfirmationRequirement.REQUIRED_BY_PLATFORM,
            verificationMechanism = null,
            knownRestrictions = "Redirects all calls; confirm charges",
            evidenceSource = null, dateLastChecked = null, confidence = "none",
            manualFallbackInstructionsKey = null
        ),
        // Robi
        CarrierCapability(
            carrierName = "Robi", countryIso = "BD", condition = ForwardingCondition.UNREACHABLE,
            userConfirmation = ConfirmationRequirement.UNKNOWN,
            verificationMechanism = null,
            knownRestrictions = "Not evidenced",
            evidenceSource = null, dateLastChecked = null, confidence = "none",
            manualFallbackInstructionsKey = null
        ),
        CarrierCapability(
            carrierName = "Robi", countryIso = "BD", condition = ForwardingCondition.BUSY,
            userConfirmation = ConfirmationRequirement.UNKNOWN,
            verificationMechanism = null,
            knownRestrictions = "Not evidenced",
            evidenceSource = null, dateLastChecked = null, confidence = "none",
            manualFallbackInstructionsKey = null
        ),
        CarrierCapability(
            carrierName = "Robi", countryIso = "BD", condition = ForwardingCondition.UNANSWERED,
            userConfirmation = ConfirmationRequirement.UNKNOWN,
            verificationMechanism = null,
            knownRestrictions = "Not evidenced",
            evidenceSource = null, dateLastChecked = null, confidence = "none",
            manualFallbackInstructionsKey = null
        ),
        CarrierCapability(
            carrierName = "Robi", countryIso = "BD", condition = ForwardingCondition.UNCONDITIONAL,
            userConfirmation = ConfirmationRequirement.UNKNOWN,
            verificationMechanism = null,
            knownRestrictions = "Not evidenced",
            evidenceSource = null, dateLastChecked = null, confidence = "none",
            manualFallbackInstructionsKey = null
        ),
        // Banglalink
        CarrierCapability(
            carrierName = "Banglalink", countryIso = "BD", condition = ForwardingCondition.UNREACHABLE,
            userConfirmation = ConfirmationRequirement.UNKNOWN,
            verificationMechanism = null,
            knownRestrictions = "Not evidenced",
            evidenceSource = null, dateLastChecked = null, confidence = "none",
            manualFallbackInstructionsKey = null
        ),
        CarrierCapability(
            carrierName = "Banglalink", countryIso = "BD", condition = ForwardingCondition.BUSY,
            userConfirmation = ConfirmationRequirement.UNKNOWN,
            verificationMechanism = null,
            knownRestrictions = "Not evidenced",
            evidenceSource = null, dateLastChecked = null, confidence = "none",
            manualFallbackInstructionsKey = null
        ),
        CarrierCapability(
            carrierName = "Banglalink", countryIso = "BD", condition = ForwardingCondition.UNANSWERED,
            userConfirmation = ConfirmationRequirement.UNKNOWN,
            verificationMechanism = null,
            knownRestrictions = "Not evidenced",
            evidenceSource = null, dateLastChecked = null, confidence = "none",
            manualFallbackInstructionsKey = null
        ),
        CarrierCapability(
            carrierName = "Banglalink", countryIso = "BD", condition = ForwardingCondition.UNCONDITIONAL,
            userConfirmation = ConfirmationRequirement.UNKNOWN,
            verificationMechanism = null,
            knownRestrictions = "Not evidenced",
            evidenceSource = null, dateLastChecked = null, confidence = "none",
            manualFallbackInstructionsKey = null
        ),
        // Teletalk
        CarrierCapability(
            carrierName = "Teletalk", countryIso = "BD", condition = ForwardingCondition.UNREACHABLE,
            userConfirmation = ConfirmationRequirement.UNKNOWN,
            verificationMechanism = null,
            knownRestrictions = "Not evidenced",
            evidenceSource = null, dateLastChecked = null, confidence = "none",
            manualFallbackInstructionsKey = null
        ),
        CarrierCapability(
            carrierName = "Teletalk", countryIso = "BD", condition = ForwardingCondition.BUSY,
            userConfirmation = ConfirmationRequirement.UNKNOWN,
            verificationMechanism = null,
            knownRestrictions = "Not evidenced",
            evidenceSource = null, dateLastChecked = null, confidence = "none",
            manualFallbackInstructionsKey = null
        ),
        CarrierCapability(
            carrierName = "Teletalk", countryIso = "BD", condition = ForwardingCondition.UNANSWERED,
            userConfirmation = ConfirmationRequirement.UNKNOWN,
            verificationMechanism = null,
            knownRestrictions = "Not evidenced",
            evidenceSource = null, dateLastChecked = null, confidence = "none",
            manualFallbackInstructionsKey = null
        ),
        CarrierCapability(
            carrierName = "Teletalk", countryIso = "BD", condition = ForwardingCondition.UNCONDITIONAL,
            userConfirmation = ConfirmationRequirement.UNKNOWN,
            verificationMechanism = null,
            knownRestrictions = "Not evidenced",
            evidenceSource = null, dateLastChecked = null, confidence = "none",
            manualFallbackInstructionsKey = null
        )
    )
}
