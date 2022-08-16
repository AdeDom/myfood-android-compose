package com.adedom.connectivity.view_model

import com.adedom.connectivity.connectivity.ConnectivityObserver
import com.adedom.connectivity.event.ConnectivityUiEvent
import com.adedom.connectivity.state.ConnectivityUiState
import com.adedom.ui_components.base.BaseViewModel

class ConnectivityViewModel(
    private val connectivityObserver: ConnectivityObserver,
) : BaseViewModel<ConnectivityUiState, ConnectivityUiEvent>(ConnectivityUiState()) {

    init {
        launch {
            connectivityObserver.observe().collect { status ->
                uiState = uiState.copy(status = status)
            }
        }
    }

    fun onDismissRequest() {
        uiState = uiState.copy(status = ConnectivityUiState.Status.Unknown)
    }
}