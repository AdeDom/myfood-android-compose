package com.adedom.connectivity.data.providers.connectivity

import com.adedom.connectivity.data.models.Status
import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {

    fun observe(): Flow<Status>
}