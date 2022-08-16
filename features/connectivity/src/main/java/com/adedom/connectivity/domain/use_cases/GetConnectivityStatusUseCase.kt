package com.adedom.connectivity.domain.use_cases

import com.adedom.connectivity.data.models.Status
import com.adedom.connectivity.domain.repositories.ConnectivityRepository
import kotlinx.coroutines.flow.Flow

class GetConnectivityStatusUseCase(
    private val connectivityRepository: ConnectivityRepository,
) {

    operator fun invoke(): Flow<Status> {
        return connectivityRepository.getConnectivityStatus()
    }
}