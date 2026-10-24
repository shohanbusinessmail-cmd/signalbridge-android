package com.signalbridge.app.domain.repository

import com.signalbridge.app.domain.model.ForwardingProfile
import kotlinx.coroutines.flow.Flow

interface ForwardingProfileRepository {
    fun profiles(): Flow<List<ForwardingProfile>>
    fun profile(id: String): ForwardingProfile?
    fun save(profile: ForwardingProfile)
    fun delete(id: String)
    fun setDefault(id: String)
    fun defaultProfile(): ForwardingProfile?
}
