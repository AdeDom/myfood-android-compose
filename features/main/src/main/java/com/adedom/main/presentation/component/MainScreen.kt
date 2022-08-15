package com.adedom.main.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.adedom.main.presentation.event.MainUiEvent
import com.adedom.main.presentation.state.MainUiState
import com.adedom.main.presentation.view_model.MainViewModel
import com.adedom.ui_components.components.AppErrorAlertDialog
import org.kodein.di.compose.rememberInstance

@Composable
fun MainScreen(
    onNavigate: (MainUiEvent) -> Unit,
) {
    val viewModel: MainViewModel by rememberInstance()

    val state = viewModel.uiState

    LaunchedEffect(viewModel.uiEvent) {
        viewModel.uiEvent.collect { uiEvent ->
            onNavigate(uiEvent)
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background,
    ) {
        if (state.isLoading) {
            CircularProgressIndicator()
        }

        if (state.error != null) {
            AppErrorAlertDialog(
                error = state.error,
                onDismiss = viewModel::callMainContent,
            )
        } else {
            MainContent(viewModel, state)
        }
    }
}

@Composable
private fun MainContent(
    viewModel: MainViewModel,
    state: MainUiState,
) {
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
            modifier = Modifier.align(Alignment.BottomCenter),
        ) {
            Button(
                onClick = {
                    viewModel.onFoodDetailEvent(11)
                },
            ) {
                Text(text = "Tom Yum Goong")
            }
            Button(
                onClick = {
                    viewModel.onFoodDetailEvent(31)
                },
            ) {
                Text(text = "Som Tam")
            }
        }

        Column(
            modifier = Modifier.align(Alignment.Center),
        ) {
            Text("userId : ${state.userProfile?.userId}")
            Text("email : ${state.userProfile?.email}")
            Text("name : ${state.userProfile?.name}")
            Text("mobileNo : ${state.userProfile?.mobileNo}")
            Text("address : ${state.userProfile?.address}")
            Text("image : ${state.userProfile?.image}")
        }
    }
}