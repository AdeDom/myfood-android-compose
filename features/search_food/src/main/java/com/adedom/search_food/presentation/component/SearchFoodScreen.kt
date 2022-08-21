package com.adedom.search_food.presentation.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adedom.search_food.presentation.state.SearchFoodUiState
import com.adedom.search_food.presentation.view_model.SearchFoodViewModel
import com.adedom.ui_components.components.AppIcon
import com.adedom.ui_components.components.AppTextField
import com.adedom.ui_components.components.FoodBoxItem
import com.adedom.ui_components.theme.MyFoodTheme
import org.kodein.di.compose.rememberInstance

@Composable
fun SearchFoodScreen() {
    val viewModel: SearchFoodViewModel by rememberInstance()

    SearchFoodContent(
        state = viewModel.uiState,
        onSearchChange = { search ->
            viewModel.setSearch(search)
            viewModel.onSearchFood(search)
        },
        onFoodClick = viewModel::onFoodDetailEvent,
    )
}

@Composable
fun SearchFoodContent(
    state: SearchFoodUiState,
    onSearchChange: (String) -> Unit,
    onFoodClick: (Long) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        item {
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
            state = SearchFoodUiState(),
            onSearchChange = {},
            onFoodClick = {},
        )
    }
}