package com.signalbridge.app.ui.state

import com.signalbridge.app.app.SignalBridgeApplication
import com.signalbridge.app.domain.model.ForwardingProfile
import kotlinx.coroutines.flow.*

class ProfilesViewModel(app: SignalBridgeApplication) : ScreenViewModel(app) {

    data class UiState(
        val profiles: List<ForwardingProfile> = emptyList(),
        val isLoading: Boolean = false,
        val error: String? = null
    )

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        load()
    }

    fun load() {
        _state.value = _state.value.copy(isLoading = true)
        launch {
            val profiles = app.profileRepository.profiles().first()
            _state.value = UiState(profiles = profiles, isLoading = false)
        }
    }

    fun delete(profile: ForwardingProfile) {
        app.profileRepository.delete(profile.id)
        load()
    }

    fun setDefault(profile: ForwardingProfile) {
        app.profileRepository.setDefault(profile.id)
        load()
    }
}
