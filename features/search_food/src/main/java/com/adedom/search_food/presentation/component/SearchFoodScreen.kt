package com.adedom.search_food.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adedom.search_food.presentation.view_model.SearchFoodUiEvent
import com.adedom.search_food.presentation.view_model.SearchFoodUiState
import com.adedom.search_food.presentation.view_model.SearchFoodViewModel
import com.adedom.ui.components.components.AppEmptyData
import com.adedom.ui.components.components.AppIcon
import com.adedom.ui.components.components.AppTextField
import com.adedom.ui.components.components.FoodBoxItem
import com.adedom.ui.components.domain.models.FoodModel
import com.adedom.ui.components.theme.MyFoodTheme
import com.adedom.ui.components.R as res

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchFoodScreen(
    viewModel: SearchFoodViewModel,
    openFoodDetailPage: (Long) -> Unit,
    onBackPressed: () -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        if (viewModel.uiState.initial == null) {
            focusRequester.requestFocus()
            keyboardController?.show()
            viewModel.onEvent(SearchFoodUiEvent.Initial)
        }
    }

    SearchFoodContent(
        state = viewModel.uiState,
        viewModel::onEvent,
        focusRequester,
        openFoodDetailPage,
        onBackPressed,
    )
}

@Composable
fun SearchFoodContent(
    state: SearchFoodUiState,
    onEvent: (SearchFoodUiEvent) -> Unit,
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
                AppIcon(
                    image = Icons.Default.ArrowBack,
                    modifier = Modifier.clickable(onClick = onBackPressed),
                    contentDescription = stringResource(id = res.string.cd_icon_back),
                )
                Spacer(modifier = Modifier.width(8.dp))
                AppTextField(
                    value = state.search,
                    onValueChange = { onEvent(SearchFoodUiEvent.SetSearch(it)) },
                    hint = stringResource(id = res.string.str_search_food),
                    leadingIcon = {
                        AppIcon(
                            Icons.Default.Search,
                            contentDescription = stringResource(id = res.string.cd_icon_search),
                        )
                    },
                    singleLine = true,
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

@Preview(
    name = "Search food content",
    group = "Feature - Search food",
    showBackground = true,
)
@Composable
fun SearchFoodContentPreview() {
    MyFoodTheme {
        SearchFoodContent(
            state = SearchFoodUiState(
                search = "Abc",
                searchList = listOf(
                    FoodModel(
                        foodId = 1,
                        foodName = "foodName",
                        alias = "alias",
                        image = "",
                        favorite = 5,
                        ratingScoreCount = "ratingScoreCount",
                        categoryId = 2,
                    ),
                    FoodModel(
                        foodId = 2,
                        foodName = "foodName",
                        alias = "alias",
                        image = "",
                        favorite = 5,
                        ratingScoreCount = "ratingScoreCount",
                        categoryId = 2,
                    ),
                    FoodModel(
                        foodId = 3,
                        foodName = "foodName",
                        alias = "alias",
                        image = "",
                        favorite = 5,
                        ratingScoreCount = "ratingScoreCount",
                        categoryId = 2,
                    ),
                    FoodModel(
                        foodId = 4,
                        foodName = "foodName",
                        alias = "alias",
                        image = "",
                        favorite = 5,
                        ratingScoreCount = "ratingScoreCount",
                        categoryId = 2,
                    ),
                    FoodModel(
                        foodId = 5,
                        foodName = "foodName",
                        alias = "alias",
                        image = "",
                        favorite = 5,
                        ratingScoreCount = "ratingScoreCount",
                        categoryId = 2,
                    ),
                    FoodModel(
                        foodId = 6,
                        foodName = "foodName",
                        alias = "alias",
                        image = "",
                        favorite = 5,
                        ratingScoreCount = "ratingScoreCount",
                        categoryId = 2,
                    ),
                    FoodModel(
                        foodId = 7,
                        foodName = "foodName",
                        alias = "alias",
                        image = "",
                        favorite = 5,
                        ratingScoreCount = "ratingScoreCount",
                        categoryId = 2,
                    ),
                    FoodModel(
                        foodId = 8,
                        foodName = "foodName",
                        alias = "alias",
                        image = "",
                        favorite = 5,
                        ratingScoreCount = "ratingScoreCount",
                        categoryId = 2,
                    ),
                    FoodModel(
                        foodId = 9,
                        foodName = "foodName",
                        alias = "alias",
                        image = "",
                        favorite = 5,
                        ratingScoreCount = "ratingScoreCount",
                        categoryId = 2,
                    ),
                    FoodModel(
                        foodId = 10,
                        foodName = "foodName",
                        alias = "alias",
                        image = "",
                        favorite = 5,
                        ratingScoreCount = "ratingScoreCount",
                        categoryId = 2,
                    ),
                ),
            ),
            onEvent = {},
            focusRequester = FocusRequester(),
            openFoodDetailPage = {},
            onBackPressed = {},
        )
    }
}