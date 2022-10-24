package com.adedom.connectivity.presentation.component

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Popup
import com.adedom.connectivity.data.models.Status
import com.adedom.connectivity.presentation.view_model.ConnectivityUiEvent
import com.adedom.connectivity.presentation.view_model.ConnectivityUiState
import com.adedom.connectivity.presentation.view_model.ConnectivityViewModel
import com.adedom.ui_components.components.AppText
import com.adedom.ui_components.theme.MyFoodTheme
import com.adedom.ui_components.R as res

@Composable
fun ConnectivityScreen(
    viewModel: ConnectivityViewModel,
) {
    ConnectivityContent(
        state = viewModel.uiState,
        viewModel::dispatch,
    )
}

@Composable
fun ConnectivityContent(
    state: ConnectivityUiState,
    dispatch: (ConnectivityUiEvent) -> Unit,
) {
    when (state.status) {
        Status.Available -> {
            OnlineNetworkPopup(dispatch)
        }
        Status.Unavailable -> {
            OfflineNetworkPopup()
        }
        Status.Losing -> {
            OfflineNetworkPopup()
        }
        Status.Lost -> {
            OfflineNetworkPopup()
        }
        Status.Unknown -> {}
    }
}

@Composable
private fun OnlineNetworkPopup(
    dispatch: (ConnectivityUiEvent) -> Unit,
) {
    Popup(
        onDismissRequest = { dispatch(ConnectivityUiEvent.DismissRequest) },
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Green)
                .semantics {
                    contentDescription = "Background network popup"
                },
        ) {
            AppText(stringResource(id = res.string.str_online))
        }
    }
}

@Composable
private fun OfflineNetworkPopup() {
    Popup {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Red)
                .semantics {
                    contentDescription = "Background network popup"
                },
        ) {
            AppText(stringResource(id = res.string.str_offline))
        }
    }
}

@Preview(
    name = "Connectivity content available",
    group = "Feature - Connectivity",
    showBackground = true,
)
@Composable
fun ConnectivityContentAvailablePreview() {
    val context = LocalContext.current
    MyFoodTheme {
        ConnectivityContent(
            state = ConnectivityUiState(
                status = Status.Available,
            ),
            dispatch = { event ->
                when (event) {
                    ConnectivityUiEvent.DismissRequest -> {
                        Toast.makeText(context, "onDismissRequest", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        )
    }
}

@Preview(
    name = "Connectivity content unavailable",
    group = "Feature - Connectivity",
    showBackground = true,
)
@Composable
fun ConnectivityContentUnavailablePreview() {
    val context = LocalContext.current
    MyFoodTheme {
        ConnectivityContent(
            state = ConnectivityUiState(
                status = Status.Unavailable,
            ),
            dispatch = { event ->
                when (event) {
                    ConnectivityUiEvent.DismissRequest -> {
                        Toast.makeText(context, "onDismissRequest", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        )
    }
}