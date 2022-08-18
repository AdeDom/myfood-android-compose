package com.adedom.main.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
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
    Box(modifier = Modifier.fillMaxSize()) {
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

        LazyColumn(
            modifier = Modifier
                .align(Alignment.Center)
                .width(250.dp),
        ) {
            items(viewModel.uiState.categoryList) { category ->
                TextCard(
                    image = category.image,
                    text = category.categoryName,
                    modifier = Modifier
                        .fillParentMaxWidth()
                        .padding(8.dp)
                        .clickable {
                            viewModel.getFoodListByCategoryId(category.categoryId)
                        },
                )
            }
            items(viewModel.uiState.foodList) { food ->
                TextCard(
                    image = food.image,
                    text = food.foodName,
                    modifier = Modifier
                        .fillParentMaxWidth()
                        .padding(8.dp),
                )
            }
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
    }
}

@Composable
fun TextCard(
    modifier: Modifier = Modifier,
    image: String,
    text: String,
) {
    Card(
        elevation = 8.dp,
        shape = RoundedCornerShape(8.dp),
        modifier = modifier,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                model = image,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(
                    width = 64.dp,
                    height = 64.dp,
                )
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = text,
                fontSize = 16.sp,
                color = Color.Black,
            )
        }
    }
}