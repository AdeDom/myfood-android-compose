package com.adedom.main.presentation.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.adedom.main.presentation.state.MainUiState
import com.adedom.ui_components.components.AppIcon
import com.adedom.ui_components.components.AppTextField

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
            AppTextField(
                value = "state.search",
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