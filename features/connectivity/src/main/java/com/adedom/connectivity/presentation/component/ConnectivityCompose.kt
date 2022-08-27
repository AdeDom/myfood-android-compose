package com.adedom.connectivity.presentation.component

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import com.adedom.connectivity.data.models.Status
import com.adedom.connectivity.presentation.view_model.ConnectivityUiAction
import com.adedom.connectivity.presentation.view_model.ConnectivityUiState
import com.adedom.connectivity.presentation.view_model.ConnectivityViewModel
import com.adedom.ui_components.theme.MyFoodTheme
import org.koin.androidx.compose.getViewModel

@Composable
fun ConnectivityScreen() {
    val viewModel = getViewModel<ConnectivityViewModel>()

    ConnectivityContent(
        state = viewModel.uiState,
        viewModel::dispatch,
    )
}

@Composable
fun ConnectivityContent(
    state: ConnectivityUiState,
    dispatch: (ConnectivityUiAction) -> Unit,
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
    dispatch: (ConnectivityUiAction) -> Unit,
) {
    Popup(
        onDismissRequest = { dispatch(ConnectivityUiAction.DismissRequest) },
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Green),
        ) {
            Text(
                text = "Online",
                color = Color.Black,
                fontSize = 16.sp,
                modifier = Modifier.align(Alignment.Center),
            )
        }
    }
}

@Composable
private fun OfflineNetworkPopup() {
    Popup {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Red),
        ) {
            Text(
                text = "Offline",
                color = Color.Black,
                fontSize = 16.sp,
                modifier = Modifier.align(Alignment.Center),
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ConnectivityContentAvailablePreview() {
    val context = LocalContext.current
    MyFoodTheme {
        ConnectivityContent(
            state = ConnectivityUiState(
                status = Status.Available,
            ),
            dispatch = { action ->
                when (action) {
                    ConnectivityUiAction.DismissRequest -> {
                        Toast.makeText(context, "onDismissRequest", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        )
    }
}

@Composable
@Preview(showBackground = true)
fun ConnectivityContentUnavailablePreview() {
    val context = LocalContext.current
    MyFoodTheme {
        ConnectivityContent(
            state = ConnectivityUiState(
                status = Status.Unavailable,
            ),
            dispatch = { action ->
                when (action) {
                    ConnectivityUiAction.DismissRequest -> {
                        Toast.makeText(context, "onDismissRequest", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        )
    }
}