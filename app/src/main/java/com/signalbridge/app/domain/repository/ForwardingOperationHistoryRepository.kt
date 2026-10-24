package com.signalbridge.app.domain.repository

import com.signalbridge.app.domain.model.ForwardingOperation

interface ForwardingOperationHistoryRepository {
    fun records(): List<ForwardingOperation>
    fun add(record: ForwardingOperation)
    fun clear()
}
