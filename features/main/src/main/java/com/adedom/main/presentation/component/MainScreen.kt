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
import com.adedom.main.domain.models.UserProfileModel
import com.adedom.main.presentation.event.MainUiEvent
import com.adedom.main.presentation.view_model.MainViewModel
import com.adedom.ui_components.components.AppErrorAlertDialog
import org.kodein.di.compose.rememberInstance

@Composable
fun MainScreen(
    mainSaveState: MainUiEvent.SaveState?,
    onNavigate: (MainUiEvent) -> Unit,
) {
    val viewModel: MainViewModel by rememberInstance()

    val state = viewModel.uiState

    LaunchedEffect(viewModel.uiEvent) {
        viewModel.uiEvent.collect { uiEvent ->
            onNavigate(uiEvent)
        }
    }

    LaunchedEffect(key1 = viewModel) {
        if (mainSaveState?.userProfile != null && mainSaveState.mainContent != null) {
            viewModel.setInitState(mainSaveState.userProfile, mainSaveState.mainContent)
        } else {
            viewModel.callMainContent()
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background,
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                )
            }

            if (state.error != null) {
                AppErrorAlertDialog(
                    error = state.error,
                    onDismiss = viewModel::callMainContent,
                )
            }

            if (state.userProfile != null && state.mainContent != null) {
                MainContent(viewModel, state.userProfile)
            }
        }
    }
}

@Composable
private fun MainContent(
    viewModel: MainViewModel,
    userProfile: UserProfileModel,
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
            Text("userId : ${userProfile.userId}")
            Text("email : ${userProfile.email}")
            Text("name : ${userProfile.name}")
            Text("mobileNo : ${userProfile.mobileNo}")
            Text("address : ${userProfile.address}")
            Text("image : ${userProfile.image}")
        }
    }
}