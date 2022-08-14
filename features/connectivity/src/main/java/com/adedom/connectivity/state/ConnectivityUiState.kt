package com.adedom.connectivity.state

data class ConnectivityUiState(
    val status: Status = Status.Unknown,
) {

    enum class Status {
        Available,
        Unavailable,
        Losing,
        Lost,
        Unknown,
    }
}