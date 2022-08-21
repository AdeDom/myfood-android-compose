package com.adedom.main.presentation.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.adedom.main.presentation.state.MainUiState

@Composable
fun SearchContent(
    state: MainUiState,
    modifier: Modifier = Modifier,
    onSearchChange: (String) -> Unit,
    onFoodClick: (Long) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
    ) {
        item {
            SearchTextField(
                value = state.search,
                onSearchChange = onSearchChange,
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