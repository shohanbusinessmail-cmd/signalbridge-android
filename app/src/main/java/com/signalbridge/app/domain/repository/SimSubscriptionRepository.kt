package com.signalbridge.app.domain.repository

import com.signalbridge.app.domain.model.SimSubscription
import kotlinx.coroutines.flow.Flow

interface SimSubscriptionRepository {
    /** Emits the currently accessible subscriptions whenever they change. */
    fun subscriptions(): Flow<List<SimSubscription>>

    /** One-time read of current subscriptions. */
    fun currentSubscriptions(): List<SimSubscription>

    /** Refresh from the platform (may require permissions). */
    fun refresh(): List<SimSubscription>

    /** Whether a given subscription id is still present/accessible. */
    fun isKnown(subscriptionId: Int): Boolean
}
