package com.signalbridge.app.ui.state

import com.signalbridge.app.app.SignalBridgeApplication

class PermissionRecoveryViewModel(app: SignalBridgeApplication) : ScreenViewModel(app) {

    data class UiState(
        val capability: String = "",
        val rationale: String = "",
        val canRequest: Boolean = true,
        val canOpenSettings: Boolean = false,
        val isCarrierControlled: Boolean = false
    )

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    fun setCapability(capability: String, rationale: String, canOpenSettings: Boolean, isCarrierControlled: Boolean) {
        _state.value = UiState(
            capability = capability,
            rationale = rationale,
            canOpenSettings = canOpenSettings,
            isCarrierControlled = isCarrierControlled
        )
    }

    fun openSettings() {
        val intent = android.content.Intent(
            android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        )
        intent.data = android.net.Uri.fromParts("package", app.packageName, null)
        intent.addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK)
        app.startActivity(intent)
    }
}
