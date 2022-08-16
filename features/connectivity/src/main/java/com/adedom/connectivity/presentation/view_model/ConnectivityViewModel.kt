package com.adedom.connectivity.presentation.view_model

import com.adedom.connectivity.data.models.Status
import com.adedom.connectivity.domain.use_cases.GetConnectivityStatusUseCase
import com.adedom.connectivity.presentation.event.ConnectivityUiEvent
import com.adedom.connectivity.presentation.state.ConnectivityUiState
import com.adedom.ui_components.base.BaseViewModel

class ConnectivityViewModel(
    private val getConnectivityStatusUseCase: GetConnectivityStatusUseCase,
) : BaseViewModel<ConnectivityUiState, ConnectivityUiEvent>(ConnectivityUiState()) {

    init {
        launch {
            getConnectivityStatusUseCase().collect { status ->
                uiState = uiState.copy(status = status)
            }
        }
    }

    fun onDismissRequest() {
        uiState = uiState.copy(status = Status.Unknown)
    }
}