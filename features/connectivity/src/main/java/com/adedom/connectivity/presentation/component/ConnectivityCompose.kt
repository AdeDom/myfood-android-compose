package com.adedom.connectivity.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import com.adedom.connectivity.data.models.Status
import com.adedom.connectivity.presentation.view_model.ConnectivityViewModel
import org.kodein.di.compose.rememberInstance

@Composable
fun ConnectivityCompose() {
    val viewModel by rememberInstance<ConnectivityViewModel>()

    when (viewModel.uiState.status) {
        Status.Available -> {
            OnlineNetworkPopup(viewModel::onDismissRequest)
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
    onDismissRequest: () -> Unit,
) {
    Popup(
        onDismissRequest = onDismissRequest,
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
