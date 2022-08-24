package com.adedom.search_food.presentation.view_model

import androidx.lifecycle.viewModelScope
import com.adedom.search_food.domain.use_cases.SearchFoodUseCase
import com.adedom.search_food.presentation.event.SearchFoodUiEvent
import com.adedom.search_food.presentation.state.SearchFoodUiState
import com.adedom.ui_components.base.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchFoodViewModel(
    private val searchFoodUseCase: SearchFoodUseCase,
) : BaseViewModel<SearchFoodUiState, SearchFoodUiEvent>(SearchFoodUiState()) {

    private var searchJob: Job? = null

    fun setSearch(search: String) {
        uiState = uiState.copy(search = search)
    }

    fun onSearchFood(search: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500)
            val foods = searchFoodUseCase(search)
            uiState = uiState.copy(searchList = foods)
        }
    }

    fun onFoodDetailEvent(foodId: Long) {
        viewModelScope.launch {
            val event = SearchFoodUiEvent.FoodDetail(foodId)
            _uiEvent.emit(event)
        }
    }

    fun onOnBackPressedEvent() {
        viewModelScope.launch {
            val event = SearchFoodUiEvent.OnBackPressed
            _uiEvent.emit(event)
        }
    }
}