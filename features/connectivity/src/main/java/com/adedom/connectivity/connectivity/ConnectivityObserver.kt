package com.adedom.connectivity.connectivity

import com.adedom.connectivity.state.ConnectivityUiState
import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {

    fun observe(): Flow<ConnectivityUiState.Status>
}