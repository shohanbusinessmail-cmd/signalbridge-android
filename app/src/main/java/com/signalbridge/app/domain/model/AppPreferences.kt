package com.signalbridge.app.domain.model

data class AppPreferences(
    val language: AppLanguage = AppLanguage.ENGLISH,
    val themeMode: ThemeMode = ThemeMode.SYSTEM,
    val defaultProfileId: String? = null,
    val defaultRecoveryPolicy: RecoveryPolicy = RecoveryPolicy.default,
    val setupComplete: Boolean = false,
    val chargesAcknowledged: Boolean = false,
    val lastSelectedSubscriptionIds: List<Int> = emptyList(),
    val carrierConfirmationRequired: Boolean = true
)
