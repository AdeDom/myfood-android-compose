package com.adedom.search_food.presentation.view_model

import com.adedom.search_food.presentation.event.SearchFoodUiEvent
import com.adedom.search_food.presentation.state.SearchFoodUiState
import com.adedom.ui_components.base.BaseViewModel

class SearchFoodViewModel : BaseViewModel<SearchFoodUiState, SearchFoodUiEvent>(SearchFoodUiState)