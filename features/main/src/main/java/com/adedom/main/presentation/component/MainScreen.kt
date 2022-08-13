package com.adedom.main.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.adedom.main.presentation.event.MainUiEvent
import com.adedom.main.presentation.view_model.MainViewModel
import org.kodein.di.compose.rememberInstance

@Composable
fun MainScreen(
    onNavigate: (MainUiEvent) -> Unit,
) {
    val viewModel: MainViewModel by rememberInstance()

    LaunchedEffect(viewModel) {
        viewModel.uiEvent.collect { uiEvent ->
            onNavigate(uiEvent)
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Button(
            onClick = {
                viewModel.callLogout()
                viewModel.onLogoutEvent()
            },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp),
        ) {
            Text("Logout")
        }

        Column(
            modifier = Modifier.align(Alignment.Center),
        ) {
            Text("userId")
            Text("email")
            Text("name")
            Text("mobileNo")
            Text("address")
            Text("image")
        }
    }
}