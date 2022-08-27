package com.adedom.connectivity.presentation.view_model

import androidx.lifecycle.viewModelScope
import com.adedom.connectivity.data.models.Status
import com.adedom.connectivity.domain.use_cases.GetConnectivityStatusUseCase
import com.adedom.ui_components.base.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

data class ConnectivityUiState(
    val status: Status = Status.Unknown,
)

sealed interface ConnectivityUiEvent

sealed interface ConnectivityUiAction {
    object DismissRequest : ConnectivityUiAction
}

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
                dispatch(ConnectivityUiAction.DismissRequest)
            }
            .launchIn(viewModelScope)
    }

    fun dispatch(action: ConnectivityUiAction) {
        viewModelScope.launch {
            when (action) {
                ConnectivityUiAction.DismissRequest -> {
                    uiState = uiState.copy(status = Status.Unknown)
                }
            }
        }
    }
}