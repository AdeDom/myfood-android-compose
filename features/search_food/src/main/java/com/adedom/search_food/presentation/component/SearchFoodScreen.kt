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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalTextInputService
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adedom.core.domain.models.FoodModel
import com.adedom.search_food.presentation.view_model.SearchFoodUiAction
import com.adedom.search_food.presentation.view_model.SearchFoodUiState
import com.adedom.search_food.presentation.view_model.SearchFoodViewModel
import com.adedom.ui_components.components.AppEmptyData
import com.adedom.ui_components.components.AppIcon
import com.adedom.ui_components.components.AppTextField
import com.adedom.ui_components.components.FoodBoxItem
import com.adedom.ui_components.theme.MyFoodTheme

@Composable
fun SearchFoodScreen(
    viewModel: SearchFoodViewModel,
    openFoodDetailPage: (Long) -> Unit,
    onBackPressed: () -> Unit,
) {
    val inputService = LocalTextInputService.current
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        if (viewModel.uiState.initial == null) {
            focusRequester.requestFocus()
            inputService?.showSoftwareKeyboard()
            viewModel.dispatch(SearchFoodUiAction.Initial)
        }
    }

    SearchFoodContent(
        state = viewModel.uiState,
        viewModel::dispatch,
        focusRequester,
        openFoodDetailPage,
        onBackPressed,
    )
}

@Composable
fun SearchFoodContent(
    state: SearchFoodUiState,
    dispatch: (SearchFoodUiAction) -> Unit,
    focusRequester: FocusRequester,
    openFoodDetailPage: (Long) -> Unit,
    onBackPressed: () -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
    ) {
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(4.dp),
            ) {
                Column {
                    AppIcon(
                        image = Icons.Default.ArrowBack,
                        modifier = Modifier.clickable(onClick = onBackPressed),
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                }
                Spacer(modifier = Modifier.width(8.dp))
                AppTextField(
                    value = state.search,
                    onValueChange = { dispatch(SearchFoodUiAction.SetSearch(it)) },
                    hint = "Search food",
                    leadingIcon = {
                        AppIcon(Icons.Default.Search)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester),
                )
            }
        }

        if (state.searchList.isEmpty()) {
            item {
                AppEmptyData(
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        } else {
            items(state.searchList) { food ->
                FoodBoxItem(
                    food = food,
                    onFoodClick = openFoodDetailPage,
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun SearchFoodContentPreview() {
    MyFoodTheme {
        val inputService = LocalTextInputService.current
        val focusRequester = remember { FocusRequester() }

        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
            inputService?.showSoftwareKeyboard()
        }

        SearchFoodContent(
            state = SearchFoodUiState(
                search = "Abc",
                searchList = listOf(
                    FoodModel(
                        foodId = 1,
                        foodName = "foodName",
                        alias = "alias",
                        image = "",
                        ratingScoreCount = "ratingScoreCount",
                        categoryId = 2,
                    ),
                    FoodModel(
                        foodId = 2,
                        foodName = "foodName",
                        alias = "alias",
                        image = "",
                        ratingScoreCount = "ratingScoreCount",
                        categoryId = 2,
                    ),
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
            ),
            dispatch = {},
            focusRequester = focusRequester,
            openFoodDetailPage = {},
            onBackPressed = {},
        )
    }
}