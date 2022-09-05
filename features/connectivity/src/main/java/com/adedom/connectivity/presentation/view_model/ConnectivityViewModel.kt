package com.adedom.connectivity.presentation.view_model

import com.adedom.connectivity.data.models.Status
import com.adedom.connectivity.domain.use_cases.GetConnectivityStatusUseCase
import com.adedom.ui_components.base.BaseMvi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

data class ConnectivityUiState(
    val status: Status = Status.Unknown,
)

sealed interface ConnectivityUiAction {
    object DismissRequest : ConnectivityUiAction
}

class ConnectivityViewModel(
    getConnectivityStatusUseCase: GetConnectivityStatusUseCase,
) : BaseMvi<ConnectivityUiState, ConnectivityUiAction>(ConnectivityUiState()) {

    init {
        getConnectivityStatusUseCase()
            .onEach { status ->
                setState { copy(status = status) }
            }
            .filter { it == Status.Available }
            .onEach {
                delay(3000)
                dispatch(ConnectivityUiAction.DismissRequest)
            }
            .launchIn(this)
    }

    override fun dispatch(action: ConnectivityUiAction) {
        launch {
            when (action) {
                ConnectivityUiAction.DismissRequest -> {
                    setState { copy(status = Status.Unknown) }
                }
            }
        }
    }
}