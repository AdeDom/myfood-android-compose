package com.adedom.main.presentation.component

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.adedom.main.R
import com.adedom.main.presentation.view_model.HomeChannel
import com.adedom.main.presentation.view_model.HomeUiEvent
import com.adedom.main.presentation.view_model.HomeUiState
import com.adedom.main.presentation.view_model.HomeViewModel
import com.adedom.ui_components.components.AppIcon
import com.adedom.ui_components.components.AppText
import com.adedom.ui_components.components.AppTitleText
import kotlinx.coroutines.launch
import com.adedom.ui_components.R as res

@ExperimentalMaterialApi
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
        viewModel::onEvent,
        openFoodDetailPage,
        openSearchFoodPage,
        openUserProfilePage,
        openInfoPage,
    )

    BackHandler(onBack = { viewModel.onEvent(HomeUiEvent.BackHandler) })
}

@ExperimentalMaterialApi
@Composable
fun MainContent(
    state: HomeUiState,
    onLogoutClick: () -> Unit,
    onEvent: (HomeUiEvent) -> Unit,
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
                    text = stringResource(id = res.string.app_name),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                )
                Spacer(modifier = Modifier.height(16.dp))
                DrawableItemMenu(
                    text = stringResource(id = res.string.str_home),
                    icon = {
                        AppIcon(
                            image = Icons.Default.Home,
                            contentDescription = stringResource(id = res.string.cd_icon_home),
                        )
                    },
                    onClick = {
                        scope.launch {
                            scaffoldState.drawerState.close()
                        }
                    },
                )
                DrawableItemMenu(
                    text = stringResource(id = res.string.str_info),
                    icon = {
                        AppIcon(
                            image = Icons.Default.Info,
                            contentDescription = stringResource(id = res.string.cd_icon_info),
                        )
                    },
                    onClick = openInfoPage,
                )
                Spacer(modifier = Modifier.weight(1f))
                if (state.isExitAuth) {
                    DrawableItemMenu(
                        text = stringResource(id = res.string.str_logout),
                        icon = {
                            AppIcon(
                                image = painterResource(id = R.drawable.ic_logout_gray),
                                contentDescription = stringResource(id = res.string.cd_icon_logout),
                            )
                        },
                        onClick = {
                            onEvent(HomeUiEvent.Logout)
                            scope.launch {
                                scaffoldState.drawerState.close()
                            }
                        },
                    )
                } else {
                    DrawableItemMenu(
                        text = stringResource(id = res.string.str_back_to_welcome),
                        icon = {
                            AppIcon(
                                image = painterResource(id = R.drawable.ic_logout_gray),
                                contentDescription = stringResource(id = res.string.cd_icon_logout),
                            )
                        },
                        onClick = { onEvent(HomeUiEvent.NavLogout) },
                    )
                }
            }
        }
    ) {
        HomePage(
            state = state,
            onEvent = onEvent,
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
