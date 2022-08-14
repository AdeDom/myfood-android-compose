package com.adedom.connectivity.view_model

import com.adedom.connectivity.connectivity.ConnectivityObserver
import com.adedom.connectivity.event.ConnectivityUiEvent
import com.adedom.connectivity.state.ConnectivityUiState
import com.adedom.core.base.BaseViewModel

class ConnectivityViewModel(
    private val connectivity: ConnectivityObserver,
) : BaseViewModel<ConnectivityUiState, ConnectivityUiEvent>(
    ConnectivityUiState()
) {

    init {
        launch {
            connectivity.observe().collect { status ->
                uiState = uiState.copy(status = status)
            }
        }
    }

    fun onClick() {
        uiState = uiState.copy(status = ConnectivityUiState.Status.Unknown)
    }
}