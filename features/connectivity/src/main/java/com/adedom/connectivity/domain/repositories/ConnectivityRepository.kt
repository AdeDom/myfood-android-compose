package com.adedom.connectivity.domain.repositories

import com.adedom.connectivity.data.models.Status
import kotlinx.coroutines.flow.Flow

interface ConnectivityRepository {

    fun getConnectivityStatus(): Flow<Status>
}