package com.signalbridge.app.data.repository

import android.content.Context
import com.signalbridge.app.data.local.AppDatabase
import com.signalbridge.app.data.local.ProfileDao
import com.signalbridge.app.data.local.ProfileEntity
import com.signalbridge.app.domain.model.ForwardingProfile
import com.signalbridge.app.domain.model.RecoveryPolicy
import com.signalbridge.app.domain.model.SubscriptionForwardingState
import com.signalbridge.app.domain.repository.ForwardingProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ForwardingProfileRepositoryImpl(
    private val context: Context,
    private val dao: ProfileDao
) : ForwardingProfileRepository {

    override fun profiles(): Flow<List<ForwardingProfile>> =
        dao.all().map { it.map { toDomain(it) } }

    override fun profile(id: String): ForwardingProfile? =
        runBlockingCautiously { dao.findById(id) }?.let { toDomain(it) }

    override fun save(profile: ForwardingProfile) {
        runBlockingCautiously {
            dao.insert(toEntity(profile))
        }
    }

    override fun delete(id: String) {
        runBlockingCautiously { dao.findById(id)?.let { dao.delete(it) } }
    }

    override fun setDefault(id: String) {
        runBlockingCautiously {
            dao.clearDefault()
            dao.setDefault(id)
        }
    }

    override fun defaultProfile(): ForwardingProfile? =
        runBlockingCautiously { dao.findDefault() }?.let { toDomain(it) }

    private fun runBlockingCautiously(block: () -> Unit) = block()
    private suspend fun <T> runBlockingCautiously(block: suspend () -> T): T? = block()

    private fun toEntity(profile: ForwardingProfile): ProfileEntity =
        ProfileEntity(
            id = profile.id,
            name = profile.name,
            description = profile.description,
            recoveryPolicy = profile.recoveryPolicy.displayKey,
            isDefault = profile.isDefault,
            createdAtMs = profile.createdAtMs,
            updatedAtMs = profile.updatedAtMs,
            notes = profile.notes
        )

    private fun toDomain(entity: ProfileEntity): ForwardingProfile {
        // Subscriptions are stored in a separate join table in a fuller schema;
        // here we persist the structural metadata and rehydrate subscriptions
        // from the profile's subscription map when a fuller schema is added.
        return ForwardingProfile(
            id = entity.id,
            name = entity.name,
            description = entity.description,
            subscriptions = emptyMap(),
            recoveryPolicy = RecoveryPolicy.values().firstOrNull {
                it.displayKey == entity.recoveryPolicy
            } ?: RecoveryPolicy.default,
            isDefault = entity.isDefault,
            createdAtMs = entity.createdAtMs,
            updatedAtMs = entity.updatedAtMs,
            notes = entity.notes
        )
    }
}
