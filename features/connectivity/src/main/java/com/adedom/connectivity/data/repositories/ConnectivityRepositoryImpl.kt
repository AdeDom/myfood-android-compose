package com.adedom.connectivity.data.repositories

import com.adedom.connectivity.data.models.Status
import com.adedom.connectivity.data.providers.connectivity.ConnectivityObserver
import com.adedom.connectivity.domain.repositories.ConnectivityRepository
import kotlinx.coroutines.flow.Flow

class ConnectivityRepositoryImpl(
    private val connectivityObserver: ConnectivityObserver,
) : ConnectivityRepository {

    override fun getConnectivityStatus(): Flow<Status> {
        return connectivityObserver.observe()
    }
}