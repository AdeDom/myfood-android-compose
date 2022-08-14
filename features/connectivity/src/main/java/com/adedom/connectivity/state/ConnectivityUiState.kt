package com.adedom.connectivity.state

data class ConnectivityUiState(
    val status: Status = Status.Unavailable,
) {

    enum class Status {
        Available,
        Unavailable,
        Losing,
        Lost,
    }
}