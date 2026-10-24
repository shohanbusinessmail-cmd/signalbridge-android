package com.signalbridge.app.ui.state

import com.signalbridge.app.app.SignalBridgeApplication

class AboutViewModel(app: SignalBridgeApplication) : ScreenViewModel(app) {

    data class UiState(
        val version: String = "1.0.0",
        val build: String = "1",
        val developer: String = "Shohan Khan",
        val email: String = "helloiamshohan@gmail.com"
    )

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        val packageInfo = try {
            app.packageManager.getPackageInfo(app.packageName, 0)
        } catch (e: Exception) {
            null
        }
        _state.value = UiState(
            version = packageInfo?.versionName ?: "1.0.0",
            build = packageInfo?.versionCode?.toString() ?: "1"
        )
    }

    fun openEmail() {
        val intent = android.content.Intent(android.content.Intent.ACTION_SENDTO)
        intent.data = android.net.Uri.parse("mailto:${_state.value.email}")
        intent.addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK)
        app.startActivity(intent)
    }
}
