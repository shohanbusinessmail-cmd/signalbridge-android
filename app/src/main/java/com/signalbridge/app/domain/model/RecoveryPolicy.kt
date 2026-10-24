package com.signalbridge.app.domain.model

/**
 * How the app behaves on boot / network recovery when reconciling saved state
 * with observed carrier state. The safest choice is the default.
 */
enum class RecoveryPolicy(
    val displayKey: String,
    val descriptionKey: String
) {
    PRESERVE_NETWORK_SETTINGS(
        displayKey = "policy_preserve_network",
        descriptionKey = "policy_preserve_network_description"
    ),
    RECONCILE_WITH_SAVED_PROFILE(
        displayKey = "policy_reconcile",
        descriptionKey = "policy_reconcile_description"
    ),
    RESTORE_NORMAL_USE_POLICY(
        displayKey = "policy_restore_normal",
        descriptionKey = "policy_restore_normal_description"
    ),
    MANUAL_RECOVERY(
        displayKey = "policy_manual",
        descriptionKey = "policy_manual_description"
    );

    val isSafestDefault: Boolean = this == PRESERVE_NETWORK_SETTINGS

    companion object {
        val default: RecoveryPolicy = PRESERVE_NETWORK_SETTINGS
    }
}
