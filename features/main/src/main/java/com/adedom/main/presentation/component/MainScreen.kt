package com.adedom.main.presentation.component

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.adedom.main.presentation.event.MainUiEvent
import com.adedom.main.presentation.state.MainUiState
import com.adedom.main.presentation.view_model.MainViewModel
import com.adedom.ui_components.components.*
import com.adedom.ui_components.theme.MyFoodTheme
import org.koin.androidx.compose.getViewModel

@Composable
fun MainScreen(
    onEvent: (MainUiEvent) -> Unit,
) {
    val viewModel: MainViewModel = getViewModel()

    val categories = rememberSaveable { mutableStateOf(emptyList<CategoryModel>()) }
    val categoryName = rememberSaveable { mutableStateOf("") }
    val foods = rememberSaveable { mutableStateOf(emptyList<FoodModel>()) }

    LaunchedEffect(viewModel.uiEvent) {
        viewModel.uiEvent.collect { uiEvent ->
            when (uiEvent) {
                is MainUiEvent.SaveState -> {
                    categories.value = uiEvent.categories
                    categoryName.value = uiEvent.categoryName
                    foods.value = uiEvent.foods
                }
                else -> {
                    onEvent(uiEvent)
                }
            }
        }
    }

    LaunchedEffect(key1 = Unit) {
        if (categories.value.isNotEmpty()) {
            viewModel.setInitState(categories.value, categoryName.value, foods.value)
        } else {
            viewModel.callMainContent()
        }
    }

    MainContent(
        state = viewModel.uiState,
        onLogoutClick = {
            viewModel.callLogout()
            viewModel.onLogoutEvent()
        },
        onCategoryClick = viewModel::getFoodListByCategoryId,
        onFoodClick = viewModel::onFoodDetailEvent,
        onSearchFoodEvent = viewModel::onSearchFoodEvent,
        onErrorDismiss = viewModel::callMainContent,
    )
}

@Composable
fun MainContent(
    state: MainUiState,
    onLogoutClick: () -> Unit,
    onCategoryClick: (Long) -> Unit,
    onFoodClick: (Long) -> Unit,
    onSearchFoodEvent: () -> Unit,
    onErrorDismiss: () -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        if (state.isLoading) {
            CircularProgressIndicator(
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
                        .clickable(onClick = onSearchFoodEvent),
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
                                        .clickable {
                                            onCategoryClick(category.categoryId)
                                        },
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
                            onFoodClick = onFoodClick,
                        )
                    }
                }
            }
        }

        if (state.error != null) {
            AppErrorAlertDialog(
                error = state.error,
                onDismiss = onErrorDismiss,
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
            onCategoryClick = {
                Toast.makeText(context, "onCategoryClick $it", Toast.LENGTH_SHORT).show()
            },
            onFoodClick = {
                Toast.makeText(context, "onFoodClick $it", Toast.LENGTH_SHORT).show()
            },
            onSearchFoodEvent = {
                Toast.makeText(context, "onSearchFoodEvent", Toast.LENGTH_SHORT).show()
            },
            onErrorDismiss = {
                Toast.makeText(context, "onErrorDismiss", Toast.LENGTH_SHORT).show()
            },
        )
    }
}