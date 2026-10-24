package com.signalbridge.app.ui.state

import com.signalbridge.app.app.SignalBridgeApplication
import com.signalbridge.app.domain.model.SimSubscription
import kotlinx.coroutines.flow.*

class DashboardViewModel(app: SignalBridgeApplication) : ScreenViewModel(app) {

    data class SimCard(
        val subscription: SimSubscription,
        val forwardingActive: Boolean = false,
        val condition: String? = null,
        val destination: String? = null,
        val lastVerified: String? = null,
        val status: String = "unknown" // verified | inactive | pending | unknown | unsupported | error
    )

    data class UiState(
        val sims: List<SimCard> = emptyList(),
        val isLoading: Boolean = false,
        val error: String? = null
    )

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        refresh()
    }

    fun refresh() {
        _state.value = _state.value.copy(isLoading = true)
        launch {
            val subs = app.subscriptionRepository.currentSubscriptions()
            val cards = subs.map { sub ->
                SimCard(
                    subscription = sub,
                    forwardingActive = false,
                    status = "unknown"
                )
            }
            _state.value = UiState(sims = cards, isLoading = false)
        }
    }

    fun onConfigure() = Unit
    fun onVerify() = Unit
    fun onProfiles() = Unit
    fun onDiagnostics() = Unit
}
