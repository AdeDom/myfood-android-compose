package com.adedom.main.presentation.component

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adedom.main.R
import com.adedom.main.presentation.view_model.HomeChannel
import com.adedom.main.presentation.view_model.HomeUiEvent
import com.adedom.main.presentation.view_model.HomeUiState
import com.adedom.main.presentation.view_model.HomeViewModel
import com.adedom.ui_components.components.AppIcon
import com.adedom.ui_components.components.AppText
import com.adedom.ui_components.components.AppTitleText
import com.adedom.ui_components.theme.MyFoodTheme
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    viewModel: HomeViewModel,
    onLogoutClick: () -> Unit,
    openFoodDetailPage: (Long) -> Unit,
    openSearchFoodPage: () -> Unit,
    openUserProfilePage: () -> Unit,
    openInfoPage: () -> Unit,
    onBackAlert: () -> Unit,
    onBackPressed: () -> Unit,
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.channel.collect { homeChannel ->
            when (homeChannel) {
                HomeChannel.Logout -> {
                    onLogoutClick()
                }
                HomeChannel.OnBackAlert -> {
                    onBackAlert()
                }
                HomeChannel.OnBackPressed -> {
                    onBackPressed()
                }
            }
        }
    }

    MainContent(
        state = viewModel.uiState,
        onLogoutClick = viewModel::onLogoutEvent,
        viewModel::dispatch,
        openFoodDetailPage,
        openSearchFoodPage,
        openUserProfilePage,
        openInfoPage,
    )

    BackHandler(onBack = { viewModel.dispatch(HomeUiEvent.BackHandler) })
}

@Composable
fun MainContent(
    state: HomeUiState,
    onLogoutClick: () -> Unit,
    dispatch: (HomeUiEvent) -> Unit,
    openFoodDetailPage: (Long) -> Unit,
    openSearchFoodPage: () -> Unit,
    openUserProfilePage: () -> Unit,
    openInfoPage: () -> Unit,
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
                    text = "Info",
                    icon = { AppIcon(image = Icons.Default.Info) },
                    onClick = openInfoPage,
                )
                Spacer(modifier = Modifier.weight(1f))
                if (state.isExitAuth) {
                    DrawableItemMenu(
                        text = "Logout",
                        icon = { AppIcon(image = R.drawable.ic_logout_gray) },
                        onClick = {
                            dispatch(HomeUiEvent.Logout)
                            scope.launch {
                                scaffoldState.drawerState.close()
                            }
                        },
                    )
                } else {
                    DrawableItemMenu(
                        text = "Back to the welcome",
                        icon = { AppIcon(image = R.drawable.ic_logout_gray) },
                        onClick = { dispatch(HomeUiEvent.NavLogout) },
                    )
                }
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
            onLogoutClick = onLogoutClick,
            openFoodDetailPage = openFoodDetailPage,
            openSearchFoodPage = openSearchFoodPage,
            openUserProfilePage = openUserProfilePage,
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
        AppText(
            text = text,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
@Preview(showBackground = true)
fun MainContentPreview() {
    MyFoodTheme {
        MainContent(
            state = HomeUiState(),
            onLogoutClick = {},
            dispatch = {},
            openFoodDetailPage = {},
            openSearchFoodPage = {},
            openUserProfilePage = {},
            openInfoPage = {},
        )
    }
}