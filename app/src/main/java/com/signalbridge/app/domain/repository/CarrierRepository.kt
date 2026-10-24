package com.signalbridge.app.domain.repository

import com.signalbridge.app.domain.model.CarrierRegistry

interface CarrierRepository {
    fun registry(): CarrierRegistry
}
