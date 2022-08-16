package com.adedom.connectivity.presentation.state

import com.adedom.connectivity.data.models.Status

data class ConnectivityUiState(
    val status: Status = Status.Unknown,
)