package com.signalbridge.app.data.repository

import android.content.Context
import com.signalbridge.app.data.local.AppDatabase
import com.signalbridge.app.data.local.OperationDao
import com.signalbridge.app.data.local.OperationEntity
import com.signalbridge.app.domain.model.ForwardingOperation
import com.signalbridge.app.domain.repository.ForwardingOperationHistoryRepository

class ForwardingOperationHistoryRepositoryImpl(
    private val context: Context,
    private val dao: OperationDao
) : ForwardingOperationHistoryRepository {

    override fun records(): List<ForwardingOperation> =
        runBlockingCautiously { dao.all().first() }?.map { toDomain(it) } ?: emptyList()

    override fun add(record: ForwardingOperation) {
        runBlockingCautiously { dao.insert(toEntity(record)) }
    }

    override fun clear() {
        runBlockingCautiously { dao.clearAll() }
    }

    private fun runBlockingCautiously(block: () -> Unit) = block()
    private suspend fun <T> runBlockingCautiously(block: suspend () -> T): T? = block()

    private fun toEntity(record: ForwardingOperation): OperationEntity =
        OperationEntity(
            id = record.id,
            subscriptionId = record.subscriptionId,
            condition = record.condition.name,
            destination = record.destination,
            resultType = record.resultType,
            resultSummaryKey = record.resultSummaryKey,
            timestampMs = record.timestampMs,
            carrierConfirmed = record.carrierConfirmed,
            requiresUserAction = record.requiresUserAction
        )

    private fun toDomain(entity: OperationEntity): ForwardingOperation =
        ForwardingOperation(
            id = entity.id,
            subscriptionId = entity.subscriptionId,
            condition = com.signalbridge.app.domain.model.ForwardingCondition.valueOf(
                entity.condition
            ),
            destination = entity.destination,
            resultType = entity.resultType,
            resultSummaryKey = entity.resultSummaryKey,
            timestampMs = entity.timestampMs,
            carrierConfirmed = entity.carrierConfirmed,
            requiresUserAction = entity.requiresUserAction
        )
}
