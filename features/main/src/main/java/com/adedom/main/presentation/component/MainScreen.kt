package com.adedom.main.presentation.component

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adedom.core.domain.models.FoodModel
import com.adedom.main.R
import com.adedom.main.domain.models.CategoryModel
import com.adedom.main.presentation.view_model.MainUiAction
import com.adedom.main.presentation.view_model.MainUiEvent
import com.adedom.main.presentation.view_model.MainUiState
import com.adedom.main.presentation.view_model.MainViewModel
import com.adedom.ui_components.components.*
import com.adedom.ui_components.theme.MyFoodTheme
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
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        if (state.isLoading) {
            AppLoadingLottieAnimation(
                modifier = Modifier.align(Alignment.Center),
            )
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
            ) {
                Row {
                    AppTitleText(text = "Food")
                    Spacer(modifier = Modifier.weight(1f))
                    AppIcon(
                        image = R.drawable.ic_logout_gray,
                        modifier = Modifier
                            .size(
                                width = 24.dp,
                                height = 24.dp,
                            )
                            .clickable(onClick = onLogoutClick),
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .clip(RoundedCornerShape(32.dp))
                        .background(Color.LightGray)
                        .clickable { dispatch(MainUiAction.NavSearch) },
                ) {
                    Row(
                        modifier = Modifier.align(Alignment.CenterStart),
                    ) {
                        Spacer(modifier = Modifier.width(16.dp))
                        AppIcon(Icons.Default.Search)
                        Spacer(modifier = Modifier.width(8.dp))
                        AppText(
                            text = "Search food",
                            color = Color.Gray,
                        )
                    }
                }

                LazyColumn {
                    item {
                        LazyRow {
                            items(state.categories) { category ->
                                Box(
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .clickable { dispatch(MainUiAction.CategoryClick(category.categoryId)) },
                                ) {
                                    Card(
                                        shape = RoundedCornerShape(8.dp),
                                        elevation = 8.dp,
                                    ) {
                                        Column(
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                        ) {
                                            AppImageNetwork(
                                                image = category.image,
                                                modifier = Modifier.size(
                                                    width = 100.dp,
                                                    height = 100.dp,
                                                ),
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
                    }

                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        AppTitleText(text = state.categoryName)
                    }

                    items(state.foods) { food ->
                        FoodBoxItem(
                            food = food,
                            onFoodClick = { dispatch(MainUiAction.FoodClick(it)) },
                        )
                    }
                }
            }
        }

        if (state.error != null) {
            AppErrorAlertDialog(
                error = state.error,
                onDismiss = { dispatch(MainUiAction.ErrorDismiss) },
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun MainContentPreview() {
    val context = LocalContext.current
    MyFoodTheme {
        MainContent(
            state = MainUiState(
                categories = listOf(
                    CategoryModel(
                        categoryId = 1,
                        categoryName = "Category 1",
                        image = "",
                    ),
                    CategoryModel(
                        categoryId = 2,
                        categoryName = "Category 2",
                        image = "",
                    ),
                ),
                categoryName = "categoryName",
                foods = listOf(
                    FoodModel(
                        foodId = 3,
                        foodName = "foodName",
                        alias = "alias",
                        image = "",
                        ratingScoreCount = "ratingScoreCount",
                        categoryId = 2,
                    ),
                    FoodModel(
                        foodId = 4,
                        foodName = "foodName",
                        alias = "alias",
                        image = "",
                        ratingScoreCount = "ratingScoreCount",
                        categoryId = 2,
                    ),
                ),
            ),
            onLogoutClick = {
                Toast.makeText(context, "onLogoutClick", Toast.LENGTH_SHORT).show()
            },
            dispatch = { action ->
                when (action) {
                    is MainUiAction.CategoryClick -> {
                        Toast.makeText(
                            context,
                            "CategoryClick ${action.categoryId}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    is MainUiAction.FoodClick -> {
                        Toast.makeText(
                            context,
                            "FoodClick ${action.foodId}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    MainUiAction.NavSearch -> {
                        Toast.makeText(context, "NavSearch", Toast.LENGTH_SHORT).show()
                    }
                    MainUiAction.ErrorDismiss -> {
                        Toast.makeText(context, "ErrorDismiss", Toast.LENGTH_SHORT).show()
                    }
                    MainUiAction.BackHandler -> {
                        Toast.makeText(context, "BackHandler", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        )
    }
}