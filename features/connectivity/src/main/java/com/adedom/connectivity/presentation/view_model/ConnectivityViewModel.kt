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

sealed interface ConnectivityUiEvent {
    object DismissRequest : ConnectivityUiEvent
}

class ConnectivityViewModel(
    getConnectivityStatusUseCase: GetConnectivityStatusUseCase,
) : BaseViewModel<ConnectivityUiEvent, ConnectivityUiState>(ConnectivityUiState()) {

    init {
        getConnectivityStatusUseCase()
            .onEach { status ->
                emit { copy(status = status) }
            }
            .filter { it == Status.Available }
            .onEach {
                delay(3_000)
                onEvent(ConnectivityUiEvent.DismissRequest)
            }
            .launchIn(viewModelScope)
    }

    override fun onEvent(event: ConnectivityUiEvent) {
        viewModelScope.launch {
            when (event) {
                ConnectivityUiEvent.DismissRequest -> {
                    emit { copy(status = Status.Unknown) }
                }
            }
        }
    }
}