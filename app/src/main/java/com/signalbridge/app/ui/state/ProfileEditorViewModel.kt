package com.signalbridge.app.ui.state

import com.signalbridge.app.app.SignalBridgeApplication
import com.signalbridge.app.domain.model.ForwardingProfile
import com.signalbridge.app.domain.model.RecoveryPolicy
import com.signalbridge.app.domain.model.SubscriptionForwardingState
import java.util.UUID

class ProfileEditorViewModel(app: SignalBridgeApplication) : ScreenViewModel(app) {

    data class UiState(
        val profile: ForwardingProfile? = null,
        val name: String = "",
        val description: String? = null,
        val recoveryPolicy: RecoveryPolicy = RecoveryPolicy.default,
        val isLoading: Boolean = false,
        val error: String? = null,
        val isNew: Boolean = true
    )

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    fun open(profileId: String?) {
        if (profileId == null || profileId.isBlank()) {
            _state.value = UiState(isNew = true, name = "", recoveryPolicy = RecoveryPolicy.default)
            return
        }
        val profile = app.profileRepository.profile(profileId)
        _state.value = UiState(
            profile = profile,
            name = profile?.name ?: "",
            description = profile?.description,
            recoveryPolicy = profile?.recoveryPolicy ?: RecoveryPolicy.default,
            isNew = profile == null
        )
    }

    fun save() {
        val name = _state.value.name.trim()
        if (name.isBlank()) {
            _state.value = _state.value.copy(error = "profile_name_required")
            return
        }
        val profile = ForwardingProfile(
            id = _state.value.profile?.id ?: UUID.randomUUID().toString(),
            name = name,
            description = _state.value.description,
            subscriptions = emptyMap(),
            recoveryPolicy = _state.value.recoveryPolicy,
            isDefault = false,
            notes = null
        )
        app.profileRepository.save(profile)
        _state.value = _state.value.copy(error = null)
    }
}
