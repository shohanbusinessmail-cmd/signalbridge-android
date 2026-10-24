package com.signalbridge.app.ui.state

import com.signalbridge.app.app.SignalBridgeApplication
import com.signalbridge.app.domain.model.DiagnosticFinding
import com.signalbridge.app.domain.model.DiagnosticCategory
import com.signalbridge.app.domain.model.DiagnosticStatus

class DiagnosticsViewModel(app: SignalBridgeApplication) : ScreenViewModel(app) {

    data class UiState(
        val findings: List<DiagnosticFinding> = emptyList(),
        val isRunning: Boolean = false,
        val error: String? = null
    )

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    fun runChecks() {
        _state.value = _state.value.copy(isRunning = true, error = null)
        launch {
            val findings = buildDiagnostics()
            _state.value = UiState(findings = findings, isRunning = false)
        }
    }

    private fun buildDiagnostics(): List<DiagnosticFinding> {
        val subs = app.subscriptionRepository.currentSubscriptions()
        return listOf(
            DiagnosticFinding(
                id = "telephony_capable",
                category = DiagnosticCategory.DEVICE_CAPABILITY,
                titleKey = "diag_telephony_capable",
                explanationKey = "diag_telephony_capable_expl",
                status = if (app.callForwardingGateway.isAvailable()) DiagnosticStatus.PASS else DiagnosticStatus.FAIL,
                evidenceKey = null,
                recommendedActionKey = null
            ),
            DiagnosticFinding(
                id = "sim_subscription",
                category = DiagnosticCategory.SIM_SUBSCRIPTION,
                titleKey = "diag_sim_subscription",
                explanationKey = "diag_sim_subscription_expl",
                status = if (subs.isNotEmpty()) DiagnosticStatus.PASS else DiagnosticStatus.FAIL,
                evidenceKey = null,
                recommendedActionKey = "diag_action_check_sim_settings"
            ),
            DiagnosticFinding(
                id = "permissions",
                category = DiagnosticCategory.PERMISSIONS,
                titleKey = "diag_permissions",
                explanationKey = "diag_permissions_expl",
                status = DiagnosticStatus.WARN,
                evidenceKey = null,
                recommendedActionKey = "diag_action_grant_permissions"
            ),
            DiagnosticFinding(
                id = "carrier_support",
                category = DiagnosticCategory.CARRIER_SUPPORT,
                titleKey = "diag_carrier_support",
                explanationKey = "diag_carrier_support_expl",
                status = DiagnosticStatus.UNKNOWN,
                evidenceKey = null,
                recommendedActionKey = "diag_action_view_carrier_instructions"
            ),
            DiagnosticFinding(
                id = "background_recovery",
                category = DiagnosticCategory.BACKGROUND_RECOVERY,
                titleKey = "diag_background_recovery",
                explanationKey = "diag_background_recovery_expl",
                status = DiagnosticStatus.PASS,
                evidenceKey = null,
                recommendedActionKey = null
            )
        )
    }
}
