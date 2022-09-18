package com.adedom.main.presentation.component

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adedom.main.domain.models.CategoryModel
import com.adedom.main.presentation.view_model.HomeUiEvent
import com.adedom.main.presentation.view_model.HomeUiState
import com.adedom.ui_components.components.*
import com.adedom.ui_components.domain.models.FoodModel
import com.adedom.ui_components.theme.MyFoodTheme
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun HomePage(
    modifier: Modifier = Modifier,
    state: HomeUiState,
    onMenuClick: () -> Unit,
    onLogoutClick: () -> Unit,
    dispatch: (HomeUiEvent) -> Unit,
    openFoodDetailPage: (Long) -> Unit,
    openSearchFoodPage: () -> Unit,
    openUserProfilePage: () -> Unit,
) {
    when (state.dialog) {
        HomeUiState.Dialog.Loading -> {
            AppLoadingLottieAnimation()
        }
        is HomeUiState.Dialog.Error -> {
            AppErrorAlertDialog(
                error = state.dialog.error,
                onDismiss = { dispatch(HomeUiEvent.ErrorDismiss) },
            )
        }
        HomeUiState.Dialog.Logout -> {
            AppInteractAlertDialog(
                title = "Logout",
                text = "Are you sure to logout the app?",
                confirmButton = onLogoutClick,
                dismissButton = { dispatch(HomeUiEvent.HideDialog) },
                modifier = Modifier.wrapContentSize(),
            )
        }
        null -> {
            HomeContent(
                modifier = modifier,
                state = state,
                onMenuClick = onMenuClick,
                dispatch = dispatch,
                openFoodDetailPage = openFoodDetailPage,
                openSearchFoodPage = openSearchFoodPage,
                openUserProfilePage = openUserProfilePage,
            )
        }
    }
}

@Composable
private fun HomeContent(
    modifier: Modifier = Modifier,
    state: HomeUiState,
    onMenuClick: () -> Unit,
    dispatch: (HomeUiEvent) -> Unit,
    openFoodDetailPage: (Long) -> Unit,
    openSearchFoodPage: () -> Unit,
    openUserProfilePage: () -> Unit,
) {
    Box(
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            SwipeRefresh(
                state = rememberSwipeRefreshState(state.isRefreshing),
                onRefresh = { dispatch(HomeUiEvent.Refreshing) },
            ) {
                LazyColumn {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(60.dp)
                                .padding(4.dp),
                        ) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(32.dp))
                                    .background(Color.LightGray)
                                    .clickable(onClick = openSearchFoodPage),
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Spacer(modifier = Modifier.width(8.dp))
                                    IconButton(onClick = onMenuClick) {
                                        Icon(
                                            imageVector = Icons.Default.Menu,
                                            contentDescription = null,
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(8.dp))
                                    AppText(
                                        text = "Search food",
                                        color = Color.Gray,
                                        modifier = Modifier.weight(1f),
                                    )
                                    state.imageProfile?.let {
                                        AppImageNetwork(
                                            image = state.imageProfile,
                                            modifier = Modifier
                                                .size(
                                                    width = 40.dp,
                                                    height = 40.dp,
                                                )
                                                .clip(CircleShape)
                                                .clickable(onClick = openUserProfilePage),
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(8.dp))
                                }
                            }
                        }
                    }

                    item {
                        LazyRow {
                            items(state.categories) { category ->
                                Box(
                                    modifier = Modifier
                                        .padding(4.dp)
                                        .clickable {
                                            dispatch(HomeUiEvent.CategoryClick(category.categoryId))
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
                                            Spacer(modifier = Modifier.height(4.dp))
                                            if (category.categoryId == state.categoryIdClick) {
                                                Box(
                                                    modifier = Modifier
                                                        .size(
                                                            width = 64.dp,
                                                            height = 4.dp,
                                                        )
                                                        .clip(RoundedCornerShape(4.dp))
                                                        .background(Color(0xFFFFD700)),
                                                )
                                                Spacer(modifier = Modifier.height(4.dp))
                                            } else {
                                                Spacer(modifier = Modifier.height(8.dp))
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    item {
                        Row {
                            Spacer(
                                modifier = Modifier.size(
                                    width = 4.dp,
                                    height = 4.dp,
                                ),
                            )
                            AppTitleText(text = state.categoryName)
                        }
                    }

                    if (state.foods.isEmpty()) {
                        item {
                            AppEmptyData(
                                modifier = Modifier.fillMaxWidth(),
                            )
                        }
                    } else {
                        items(state.foods) { food ->
                            FoodBoxItem(
                                food = food,
                                onFoodClick = openFoodDetailPage,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun HomePagePreview() {
    val context = LocalContext.current
    MyFoodTheme {
        HomePage(
            state = HomeUiState(
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
                    CategoryModel(
                        categoryId = 3,
                        categoryName = "Category 3",
                        image = "",
                    ),
                    CategoryModel(
                        categoryId = 4,
                        categoryName = "Category 4",
                        image = "",
                    ),
                    CategoryModel(
                        categoryId = 5,
                        categoryName = "Category 5",
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
                    FoodModel(
                        foodId = 5,
                        foodName = "foodName",
                        alias = "alias",
                        image = "",
                        ratingScoreCount = "ratingScoreCount",
                        categoryId = 2,
                    ),
                    FoodModel(
                        foodId = 6,
                        foodName = "foodName",
                        alias = "alias",
                        image = "",
                        ratingScoreCount = "ratingScoreCount",
                        categoryId = 2,
                    ),
                    FoodModel(
                        foodId = 7,
                        foodName = "foodName",
                        alias = "alias",
                        image = "",
                        ratingScoreCount = "ratingScoreCount",
                        categoryId = 2,
                    ),
                    FoodModel(
                        foodId = 8,
                        foodName = "foodName",
                        alias = "alias",
                        image = "",
                        ratingScoreCount = "ratingScoreCount",
                        categoryId = 2,
                    ),
                    FoodModel(
                        foodId = 9,
                        foodName = "foodName",
                        alias = "alias",
                        image = "",
                        ratingScoreCount = "ratingScoreCount",
                        categoryId = 2,
                    ),
                    FoodModel(
                        foodId = 10,
                        foodName = "foodName",
                        alias = "alias",
                        image = "",
                        ratingScoreCount = "ratingScoreCount",
                        categoryId = 2,
                    ),
                ),
                categoryIdClick = 2,
                imageProfile = "",
//                dialog = HomeUiState.Dialog.Error(BaseError()),
//                dialog = HomeUiState.Dialog.Logout,
            ),
            onMenuClick = {},
            onLogoutClick = {},
            dispatch = { event ->
                when (event) {
                    is HomeUiEvent.CategoryClick -> {
                        Toast.makeText(
                            context,
                            "CategoryClick ${event.categoryId}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    HomeUiEvent.ErrorDismiss -> {
                        Toast.makeText(context, "ErrorDismiss", Toast.LENGTH_SHORT).show()
                    }
                    HomeUiEvent.BackHandler -> {
                        Toast.makeText(context, "BackHandler", Toast.LENGTH_SHORT).show()
                    }
                    HomeUiEvent.Refreshing -> {
                        Toast.makeText(context, "Refreshing", Toast.LENGTH_SHORT).show()
                    }
                    is HomeUiEvent.Logout -> {}
                    HomeUiEvent.NavLogout -> {}
                    HomeUiEvent.HideDialog -> {}
                }
            },
            openFoodDetailPage = {},
            openSearchFoodPage = {},
            openUserProfilePage = {},
        )
    }
}