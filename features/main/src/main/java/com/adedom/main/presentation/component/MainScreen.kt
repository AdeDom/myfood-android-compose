package com.adedom.main.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.adedom.main.R
import com.adedom.main.presentation.event.MainUiEvent
import com.adedom.main.presentation.view_model.MainViewModel
import com.adedom.ui_components.components.AppErrorAlertDialog
import com.adedom.ui_components.components.AppTextField
import com.adedom.ui_components.components.AppTitleText
import org.kodein.di.compose.rememberInstance

@Composable
fun MainScreen(
    mainSaveState: MainUiEvent.SaveState?,
    onEvent: (MainUiEvent) -> Unit,
) {
    val viewModel: MainViewModel by rememberInstance()

    val state = viewModel.uiState

    LaunchedEffect(viewModel.uiEvent) {
        viewModel.uiEvent.collect { uiEvent ->
            onEvent(uiEvent)
        }
    }

    LaunchedEffect(key1 = viewModel) {
        if (mainSaveState != null) {
            viewModel.setInitState(mainSaveState.categoryList, mainSaveState.foodList)
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
            } else {
                MainContent(viewModel)
            }

            if (state.error != null) {
                AppErrorAlertDialog(
                    error = state.error,
                    onDismiss = viewModel::callMainContent,
                )
            }
        }
    }
}

@Composable
fun MainContent(viewModel: MainViewModel) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        LazyColumn {
            item {
                Column {
                    Row {
                        AppTitleText(text = "Food")
                        Spacer(modifier = Modifier.weight(1f))
                        Image(
                            painter = painterResource(id = R.drawable.ic_logout_gray),
                            contentDescription = null,
                            modifier = Modifier
                                .size(
                                    width = 24.dp,
                                    height = 24.dp,
                                ),
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    AppTextField(
                        value = viewModel.uiState.search,
                        onValueChange = viewModel::setSearch,
                        hint = "Search food",
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_search_black),
                                contentDescription = null,
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }
        }
    }
}