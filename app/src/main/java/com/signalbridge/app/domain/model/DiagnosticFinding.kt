package com.signalbridge.app.domain.model

enum class DiagnosticStatus {
    PASS, WARN, FAIL, UNKNOWN, NOT_APPLICABLE
}

enum class DiagnosticCategory {
    DEVICE_CAPABILITY, SIM_SUBSCRIPTION, PERMISSIONS, NETWORK_READINESS,
    CARRIER_SUPPORT, FORWARDING_CONFIGURATION, BACKGROUND_RECOVERY
}

/**
 * A single diagnostic check result. Never makes destructive changes automatically.
 */
data class DiagnosticFinding(
    val id: String,
    val category: DiagnosticCategory,
    val titleKey: String,
    val explanationKey: String,
    val status: DiagnosticStatus,
    val evidenceKey: String?,
    val recommendedActionKey: String?,
    val canRetry: Boolean = false,
    val canOpenSettings: Boolean = false,
    val instructionKey: String? = null,
    val sensitive: Boolean = false
)
