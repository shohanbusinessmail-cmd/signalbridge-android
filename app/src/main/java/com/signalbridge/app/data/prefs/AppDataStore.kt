package com.signalbridge.app.data.prefs

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.editCommit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.core.editTransform
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import com.signalbridge.app.domain.model.AppLanguage
import com.signalbridge.app.domain.model.AppPreferences
import com.signalbridge.app.domain.model.ThemeMode
import com.signalbridge.app.domain.model.RecoveryPolicy

private const val PREF_FILE_NAME = "signalbridge_preferences"

object AppDataStore {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREF_FILE_NAME)

    // Keys
    private val LANGUAGE_CODE = stringPreferencesKey("language_code")
    private val THEME_MODE = stringPreferencesKey("theme_mode")
    private val DEFAULT_PROFILE_ID = stringPreferencesKey("default_profile_id")
    private val DEFAULT_RECOVERY_POLICY = stringPreferencesKey("default_recovery_policy")
    private val SETUP_COMPLETE = booleanPreferencesKey("setup_complete")
    private val CHARGES_ACKNOWLEDGED = booleanPreferencesKey("charges_acknowledged")
    private val CARRIER_CONFIRMATION_REQUIRED = booleanPreferencesKey("carrier_confirmation_required")

    suspend fun Context.savePreferences(prefs: AppPreferences) {
        val dataStore = this.dataStore
        dataStore.edit { settings ->
            settings[LANGUAGE_CODE] = prefs.language.code
            settings[THEME_MODE] = prefs.themeMode.displayKey
            settings[DEFAULT_PROFILE_ID] = prefs.defaultProfileId
            settings[DEFAULT_RECOVERY_POLICY] = prefs.defaultRecoveryPolicy.displayKey
            settings[SETUP_COMPLETE] = prefs.setupComplete
            settings[CHARGES_ACKNOWLEDGED] = prefs.chargesAcknowledged
            settings[CARRIER_CONFIRMATION_REQUIRED] = prefs.carrierConfirmationRequired
        }
    }

    suspend fun Context.loadPreferences(): AppPreferences {
        val settings = this.dataStore.data.first()
        return AppPreferences(
            language = AppLanguage.fromCode(settings[LANGUAGE_CODE]),
            themeMode = ThemeMode.fromKey(settings[THEME_MODE]),
            defaultProfileId = settings[DEFAULT_PROFILE_ID],
            defaultRecoveryPolicy = RecoveryPolicy.values().firstOrNull {
                it.displayKey == settings[DEFAULT_RECOVERY_POLICY}
            } ?: RecoveryPolicy.default,
            setupComplete = settings[SETUP_COMPLETE],
            chargesAcknowledged = settings[CHARGES_ACKNOWLEDGED],
            lastSelectedSubscriptionIds = emptyList(),
            carrierConfirmationRequired = settings[CARRIER_CONFIRMATION_REQUIRED]
        )
    }

    suspend fun Context.updateSetupComplete(complete: Boolean) {
        this.dataStore.edit { settings -> settings[SETUP_COMPLETE] = complete }
    }

    suspend fun Context.updateChargesAcknowledged(acknowledged: Boolean) {
        this.dataStore.edit { settings -> settings[CHARGES_ACKNOWLEDGED] = acknowledged }
    }

    fun Context.preferencesFlow(): Flow<AppPreferences> {
        return this.dataStore.data.map { settings ->
            AppPreferences(
                language = AppLanguage.fromCode(settings[LANGUAGE_CODE]),
                themeMode = ThemeMode.fromKey(settings[THEME_MODE]),
                defaultProfileId = settings[DEFAULT_PROFILE_ID],
                defaultRecoveryPolicy = RecoveryPolicy.values().firstOrNull {
                    it.displayKey == settings[DEFAULT_RECOVERY_POLICY]
                } ?: RecoveryPolicy.default,
                setupComplete = settings[SETUP_COMPLETE],
                chargesAcknowledged = settings[CHARGES_ACKNOWLEDGED],
                lastSelectedSubscriptionIds = emptyList(),
                carrierConfirmationRequired = settings[CARRIER_CONFIRMATION_REQUIRED]
            )
        }
    }
}
