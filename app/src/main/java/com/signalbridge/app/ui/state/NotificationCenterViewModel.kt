package com.signalbridge.app.ui.state

import com.signalbridge.app.app.SignalBridgeApplication

class NotificationCenterViewModel(app: SignalBridgeApplication) : ScreenViewModel(app) {

    data class UiState(
        val items: List<NotificationItem> = emptyList(),
        val isLoading: Boolean = false
    ) {
        data class NotificationItem(
            val id: String,
            val titleKey: String,
            val bodyKey: String?,
            val timestampMs: Long,
            val read: Boolean = false,
            val actionKey: String? = null
        )
    }

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    fun load() {
        _state.value = _state.value.copy(isLoading = true)
        launch {
            // Notifications are derived from local operation history; no network.
            val records = app.historyRepository.records()
            val items = records.map { record ->
                UiState.NotificationItem(
                    id = record.id,
                    titleKey = "notification_event",
                    bodyKey = record.resultSummaryKey,
                    timestampMs = record.timestampMs,
                    read = false,
                    actionKey = null
                )
            }
            _state.value = UiState(items = items, isLoading = false)
        }
    }
}
