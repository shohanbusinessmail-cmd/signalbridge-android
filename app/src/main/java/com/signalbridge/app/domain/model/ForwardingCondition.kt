package com.signalbridge.app.domain.model

/**
 * The forwarding conditions supported by the domain model, mapped to
 * telephony supplementary services where the platform exposes them.
 *
 * Each condition represents a distinct carrier-managed rule. They are enabled
 * and disabled independently; enabling one condition never implicitly changes
 * another.
 */
enum class ForwardingCondition(
    val displayKey: String,
    val descriptionKey: String,
    /** Whether this condition is the primary "powered-off protection" rule. */
    val isPowerOffProtection: Boolean,
    /** Whether a timeout is meaningful for this condition. */
    val supportsTimeout: Boolean
) {
    UNCONDITIONAL(
        displayKey = "condition_unconditional",
        descriptionKey = "condition_unconditional_description",
        isPowerOffProtection = false,
        supportsTimeout = false
    ),
    UNREACHABLE(
        displayKey = "condition_unreachable",
        descriptionKey = "condition_unreachable_description",
        isPowerOffProtection = true,
        supportsTimeout = false
    ),
    BUSY(
        displayKey = "condition_busy",
        descriptionKey = "condition_busy_description",
        isPowerOffProtection = false,
        supportsTimeout = false
    ),
    UNANSWERED(
        displayKey = "condition_unanswered",
        descriptionKey = "condition_unanswered_description",
        isPowerOffProtection = false,
        supportsTimeout = true
    );

    /** All conditional rules (everything except unconditional). */
    val isConditional: Boolean = this != UNCONDITIONAL

    /** Conditions that together form the recommended "combined protection" profile. */
    companion object {
        val PROTECTION_PROFILE: List<ForwardingCondition> = listOf(UNREACHABLE, BUSY, UNANSWERED)
    }
}
