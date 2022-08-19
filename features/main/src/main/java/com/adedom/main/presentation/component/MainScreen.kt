package com.adedom.main.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.adedom.main.R
import com.adedom.main.presentation.event.MainUiEvent
import com.adedom.main.presentation.view_model.MainViewModel
import com.adedom.ui_components.components.AppErrorAlertDialog
import com.adedom.ui_components.components.AppText
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
            viewModel.setInitState(
                mainSaveState.categories,
                mainSaveState.categoryName,
                mainSaveState.foods,
            )
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
        Column {
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
                Spacer(modifier = Modifier.height(16.dp))
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

            Spacer(modifier = Modifier.height(16.dp))
            LazyRow {
                items(viewModel.uiState.categories) { category ->
                    Box(
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable {
                                viewModel.getFoodListByCategoryId(category.categoryId)
                            },
                    ) {
                        Card(
                            shape = RoundedCornerShape(8.dp),
                            elevation = 8.dp,
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {
                                AsyncImage(
                                    model = category.image,
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.size(
                                        width = 100.dp,
                                        height = 100.dp,
                                    )
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                AppText(
                                    text = category.categoryName,
                                    fontWeight = FontWeight.Bold,
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            AppTitleText(text = viewModel.uiState.categoryName)

            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn {
                items(viewModel.uiState.foods) { food ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clickable {
                                viewModel.onFoodDetailEvent(food.foodId)
                            },
                    ) {
                        Row {
                            AsyncImage(
                                model = food.image,
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(
                                        width = 100.dp,
                                        height = 100.dp,
                                    )
                                    .clip(RoundedCornerShape(8.dp)),
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Column {
                                AppText(
                                    text = food.foodName,
                                    fontWeight = FontWeight.Bold,
                                )
                                food.alias?.let {
                                    Spacer(modifier = Modifier.height(4.dp))
                                    AppText(
                                        text = food.alias,
                                        color = Color.Gray,
                                        fontSize = 14.sp,
                                    )
                                }
                                food.ratingScoreCount?.let {
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Row {
                                        Image(
                                            painter = painterResource(id = R.drawable.ic_star_amber),
                                            contentDescription = null,
                                            modifier = Modifier.size(18.dp),
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        AppText(
                                            text = food.ratingScoreCount,
                                            color = Color(0xFFFFC107),
                                            fontSize = 14.sp,
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}