package com.adedom.search_food.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adedom.core.domain.models.FoodModel
import com.adedom.search_food.presentation.event.SearchFoodUiEvent
import com.adedom.search_food.presentation.state.SearchFoodUiState
import com.adedom.search_food.presentation.view_model.SearchFoodViewModel
import com.adedom.ui_components.components.AppIcon
import com.adedom.ui_components.components.AppTextField
import com.adedom.ui_components.components.FoodBoxItem
import com.adedom.ui_components.theme.MyFoodTheme
import org.koin.androidx.compose.getViewModel

@Composable
fun SearchFoodScreen(
    onEvent: (SearchFoodUiEvent) -> Unit,
) {
    val viewModel: SearchFoodViewModel = getViewModel()

    LaunchedEffect(viewModel.uiEvent) {
        viewModel.uiEvent.collect { uiEvent ->
            onEvent(uiEvent)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.onSearchFood("")
    }

    SearchFoodContent(
        state = viewModel.uiState,
        onSearchChange = { search ->
            viewModel.setSearch(search)
            viewModel.onSearchFood(search)
        },
        onFoodClick = viewModel::onFoodDetailEvent,
        onOnBackPressedEvent = viewModel::onOnBackPressedEvent,
    )
}

@Composable
fun SearchFoodContent(
    state: SearchFoodUiState,
    onSearchChange: (String) -> Unit,
    onFoodClick: (Long) -> Unit,
    onOnBackPressedEvent: () -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column {
                    AppIcon(
                        image = Icons.Default.ArrowBack,
                        modifier = Modifier.clickable(onClick = onOnBackPressedEvent),
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                }
                Spacer(modifier = Modifier.width(8.dp))
                AppTextField(
                    value = state.search,
                    onValueChange = onSearchChange,
                    hint = "Search food",
                    leadingIcon = {
                        AppIcon(Icons.Default.Search)
                    },
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }

        items(state.searchList) { food ->
            FoodBoxItem(
                food = food,
                onFoodClick = onFoodClick,
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun SearchFoodContentPreview() {
    MyFoodTheme {
        SearchFoodContent(
            state = SearchFoodUiState(
                search = "Abc",
                searchList = listOf(
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
            onSearchChange = {},
            onFoodClick = {},
            onOnBackPressedEvent = {},
        )
    }
}