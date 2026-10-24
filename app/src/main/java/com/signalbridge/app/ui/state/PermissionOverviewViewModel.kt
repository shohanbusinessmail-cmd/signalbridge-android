package com.signalbridge.app.ui.state

import android.content.pm.PackageManager
import com.signalbridge.app.app.SignalBridgeApplication
import com.signalbridge.app.domain.model.PermissionRequirement
import com.signalbridge.app.data.telephony.TelephonyCallForwardingGateway

class PermissionOverviewViewModel(app: SignalBridgeApplication) : ScreenViewModel(app) {

    data class UiState(
        val requirements: List<PermissionRequirement> = emptyList(),
        val isLoading: Boolean = false
    )

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        refresh()
    }

    fun refresh() {
        _state.value = _state.value.copy(isLoading = true)
        launch {
            val gateway = app.callForwardingGateway
            val reqs = gateway.requiredPermissions()
            _state.value = UiState(requirements = reqs, isLoading = false)
        }
    }

    fun hasPermission(requirement: PermissionRequirement): Boolean {
        return android.content.ContextCompat.checkSelfPermission(
            app, requirement.permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun openSettings() {
        val intent = android.content.Intent(
            android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        )
        val uri = android.net.Uri.fromParts("package", app.packageName, null)
        intent.data = uri
        intent.addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK)
        app.startActivity(intent)
    }
}
