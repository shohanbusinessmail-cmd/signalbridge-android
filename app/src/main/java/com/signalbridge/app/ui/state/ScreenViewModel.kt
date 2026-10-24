package com.signalbridge.app.ui.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.signalbridge.app.app.SignalBridgeApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

/**
 * Base screen state holder. Exposes a coroutine scope bound to the ViewModel
 * so telephony operations never leak past the screen's lifetime.
 */
abstract class ScreenViewModel(application: SignalBridgeApplication) : ViewModel() {

    val app: SignalBridgeApplication = application

    protected val scope = CoroutineScope(Job() + application.coroutineScope.coroutineContext)

    /** Fire-and-forget a screen-scoped coroutine. */
    protected fun launch(block: suspend () -> Unit) = scope.launch(block)
}
