package com.adedom.connectivity.presentation.view_model

import androidx.lifecycle.viewModelScope
import com.adedom.connectivity.data.models.Status
import com.adedom.connectivity.domain.use_cases.GetConnectivityStatusUseCase
import com.adedom.connectivity.presentation.event.ConnectivityUiEvent
import com.adedom.connectivity.presentation.state.ConnectivityUiState
import com.adedom.ui_components.base.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ConnectivityViewModel(
    getConnectivityStatusUseCase: GetConnectivityStatusUseCase,
) : BaseViewModel<ConnectivityUiState, ConnectivityUiEvent>(ConnectivityUiState()) {

    init {
        getConnectivityStatusUseCase()
            .onEach { status ->
                uiState = uiState.copy(status = status)
            }
            .filter { it == Status.Available }
            .onEach {
                delay(3000)
                onDismissRequest()
            }
            .launchIn(viewModelScope)
    }

    fun onDismissRequest() {
        uiState = uiState.copy(status = Status.Unknown)
    }
}