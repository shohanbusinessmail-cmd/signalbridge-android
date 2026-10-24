package com.signalbridge.app.app

import android.app.Application
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.DefaultLifecycleObserver
import com.signalbridge.app.data.local.AppDatabase
import com.signalbridge.app.data.prefs.AppDataStore
import com.signalbridge.app.data.repository.*
import com.signalbridge.app.data.telephony.TelephonyCallForwardingGateway
import com.signalbridge.app.telephony.CallForwardingGateway
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Application-level composition root.
 *
 * Dependencies are instantiated here and exposed to the UI layer. In a larger
 * codebase this would be replaced by Hilt/DI, but a hand-wired root keeps the
 * dependency graph explicit and avoids heavyweight frameworks.
 */
class SignalBridgeApplication : Application() {

    val coroutineScope: CoroutineScope = CoroutineScope(Job() + Dispatchers.Main.immediate)

    private val database by lazy {
        AppDatabase.getDatabase(this)
    }

    val subscriptionRepository: SimSubscriptionRepository by lazy {
        SimSubscriptionRepositoryImpl(this)
    }

    val profileRepository: ForwardingProfileRepository by lazy {
        ForwardingProfileRepositoryImpl(this, database.profileDao())
    }

    val historyRepository: ForwardingOperationHistoryRepository by lazy {
        ForwardingOperationHistoryRepositoryImpl(this, database.operationDao())
    }

    val carrierRepository: CarrierRepository by lazy {
        CarrierRepositoryImpl()
    }

    val callForwardingGateway: CallForwardingGateway by lazy {
        TelephonyCallForwardingGateway(this)
    }

    /** Suspend-able preference accessors for use inside coroutines. */
    suspend fun dataStoreLoadPreferences(): AppPreferences =
        withContext(Dispatchers.Default) { AppDataStore.loadPreferences(this) }

    suspend fun dataStoreSavePreferences(prefs: AppPreferences) =
        withContext(Dispatchers.Default) { AppDataStore.savePreferences(this, prefs) }

    override fun onCreate() {
        super.onCreate()

        ProcessLifecycleOwner.get().lifecycle.addObserver(
            object : DefaultLifecycleObserver {
                override fun onStop(owner: androidx.lifecycle.LifecycleOwner) {
                    // App moved to background: no telephony commands are issued
                    // from here. Forwarding depends on the carrier rule, not a
                    // background service.
                }
            }
        )
    }
}
