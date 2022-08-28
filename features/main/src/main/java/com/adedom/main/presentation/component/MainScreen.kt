package com.adedom.main.presentation.component

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.adedom.main.R
import com.adedom.main.presentation.view_model.MainUiAction
import com.adedom.main.presentation.view_model.MainUiEvent
import com.adedom.main.presentation.view_model.MainUiState
import com.adedom.main.presentation.view_model.MainViewModel
import com.adedom.ui_components.components.AppIcon
import com.adedom.ui_components.components.AppTitleText
import com.adedom.ui_components.theme.MyFoodTheme
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@Composable
fun MainScreen(
    onEvent: (MainUiEvent) -> Unit,
) {
    val viewModel: MainViewModel = getViewModel()

    LaunchedEffect(viewModel.uiEvent) {
        viewModel.uiEvent.collect { uiEvent ->
            onEvent(uiEvent)
        }
    }

    MainContent(
        state = viewModel.uiState,
        onLogoutClick = viewModel::onLogoutEvent,
        viewModel::dispatch,
    )

    BackHandler(onBack = { viewModel.dispatch(MainUiAction.BackHandler) })
}

@Composable
fun MainContent(
    state: MainUiState,
    onLogoutClick: () -> Unit,
    dispatch: (MainUiAction) -> Unit,
) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    Scaffold(
        scaffoldState = scaffoldState,
        drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
        drawerContent = {
            Column(
                modifier = Modifier.fillMaxSize(),
            ) {
                AppTitleText(
                    text = "My Food",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                )
                Spacer(modifier = Modifier.height(16.dp))
                DrawableItemMenu(
                    text = "Home",
                    icon = { AppIcon(image = Icons.Default.Home) },
                    onClick = {
                        scope.launch {
                            scaffoldState.drawerState.close()
                        }
                    },
                )
                DrawableItemMenu(
                    text = "User profile",
                    icon = { AppIcon(image = Icons.Default.AccountCircle) },
                    onClick = {},
                )
                Spacer(modifier = Modifier.weight(1f))
                DrawableItemMenu(
                    text = "Logout",
                    icon = { AppIcon(image = R.drawable.ic_logout_gray) },
                    onClick = onLogoutClick,
                )
            }
        }
    ) {
        HomePage(
            state = state,
            dispatch = dispatch,
            onMenuClick = {
                scope.launch {
                    scaffoldState.drawerState.open()
                }
            },
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
        )
    }
}

@Composable
private fun DrawableItemMenu(
    text: String,
    icon: @Composable () -> Unit,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp)
    ) {
        icon()
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = text,
            fontSize = 18.sp,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
@Preview(showBackground = true)
fun MainContentPreview() {
    MyFoodTheme {
        MainContent(
            state = MainUiState(),
            onLogoutClick = {},
            dispatch = {},
        )
    }
}