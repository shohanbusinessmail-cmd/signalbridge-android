package com.signalbridge.app.ui.state

import com.signalbridge.app.app.SignalBridgeApplication
import com.signalbridge.app.domain.model.ForwardingCondition
import com.signalbridge.app.domain.model.RecoveryPolicy

class SmartProtectionViewModel(app: SignalBridgeApplication) : ScreenViewModel(app) {

    data class ChecklistItem(
        val id: String,
        val titleKey: String,
        val satisfied: Boolean = false
    )

    data class UiState(
        val selectedSubscriptions: List<Int> = emptyList(),
        val destination: String = "",
        val unreachableConfigured: Boolean = false,
        val recoveryPolicy: RecoveryPolicy = RecoveryPolicy.default,
        val checklist: List<ChecklistItem> = emptyList(),
        val protectionEnabled: Boolean = false,
        val isLoading: Boolean = false
    )

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        val checklist = listOf(
            ChecklistItem("sim", "checklist_sim_selected", false),
            ChecklistItem("destination", "checklist_destination_saved", false),
            ChecklistItem("capability", "checklist_carrier_capability", false),
            ChecklistItem("permissions", "checklist_permissions_granted", false),
            ChecklistItem("configured", "checklist_forwarding_configured", false),
            ChecklistItem("verified", "checklist_network_verified", false),
            ChecklistItem("charges", "checklist_charges_acknowledged", false)
        )
        _state.value = _state.value.copy(checklist = checklist)
    }

    fun toggleItem(id: String) {
        _state.value = _state.value.copy(
            checklist = _state.value.checklist.map {
                if (it.id == id) it.copy(satisfied = !it.satisfied) else it
            }
        )
    }

    fun canEnable(): Boolean =
        _state.value.selectedSubscriptions.isNotEmpty() &&
        _state.value.destination.isNotBlank() &&
        _state.value.unreachableConfigured

    fun enableProtection() {
        // Do not imply powered-off protection unless unreachable rule is configured.
        if (!canEnable()) return
        _state.value = _state.value.copy(protectionEnabled = true)
    }
}
