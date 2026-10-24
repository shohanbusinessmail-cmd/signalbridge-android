package com.signalbridge.app.data.repository

import android.content.Context
import android.telephony.SubscriptionInfo
import android.telephony.SubscriptionManager
import com.signalbridge.app.domain.model.SimSubscription
import com.signalbridge.app.domain.repository.SimSubscriptionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SimSubscriptionRepositoryImpl(private val context: Context) : SimSubscriptionRepository {

    private val manager: SubscriptionManager by lazy {
        context.getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE) as SubscriptionManager
    }

    override fun subscriptions(): Flow<List<SimSubscription>> = flow {
        emit(currentSubscriptions())
    }

    override fun currentSubscriptions(): List<SimSubscription> {
        return try {
            val infos = manager.activeSubscriptionInfoList ?: emptyList()
            infos.map { toSubscription(it) }
        } catch (e: SecurityException) {
            emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }

    override fun refresh(): List<SimSubscription> = currentSubscriptions()

    override fun isKnown(subscriptionId: Int): Boolean =
        currentSubscriptions().any { it.subscriptionId == subscriptionId }

    private fun toSubscription(info: SubscriptionInfo): SimSubscription {
        return SimSubscription(
            subscriptionId = info.subscriptionId,
            simSlotIndex = info.simSlotIndex,
            carrierName = info.carrierName?.toString(),
            countryIso = info.countryIso,
            number = info.number, // sensitive; mask before display
            isActive = info.isActive,
            isRoaming = info.isRoaming,
            dataProfile = info.dataProfile,
            subscriptionInfoHash = info.toString()
        )
    }
}
