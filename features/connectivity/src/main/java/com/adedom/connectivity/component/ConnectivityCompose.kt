package com.adedom.connectivity.component

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
import com.adedom.connectivity.state.ConnectivityUiState
import com.adedom.connectivity.view_model.ConnectivityViewModel
import org.kodein.di.compose.rememberInstance

@Composable
fun ConnectivityCompose() {
    val viewModel by rememberInstance<ConnectivityViewModel>()

    if (viewModel.uiState.status == ConnectivityUiState.Status.Available) {
        Popup {
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
    } else {
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
}