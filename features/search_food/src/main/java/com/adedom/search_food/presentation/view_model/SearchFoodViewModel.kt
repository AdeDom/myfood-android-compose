package com.adedom.search_food.presentation.view_model

import androidx.lifecycle.viewModelScope
import com.adedom.core.domain.models.FoodModel
import com.adedom.search_food.domain.use_cases.SearchFoodUseCase
import com.adedom.ui_components.base.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class SearchFoodUiState(
    val search: String = "",
    val searchList: List<FoodModel> = emptyList(),
)

sealed interface SearchFoodUiEvent {
    data class FoodDetail(val foodId: Long) : SearchFoodUiEvent
    object OnBackPressed : SearchFoodUiEvent
}

sealed interface SearchFoodUiAction {
    data class SetSearch(val value: String) : SearchFoodUiAction
    data class FoodDetail(val foodId: Long) : SearchFoodUiAction
    object OnBackPressed : SearchFoodUiAction
}

class SearchFoodViewModel(
    private val searchFoodUseCase: SearchFoodUseCase,
) : BaseViewModel<SearchFoodUiState, SearchFoodUiEvent>(SearchFoodUiState()) {

    private var searchJob: Job? = null

    init {
        viewModelScope.launch {
            delay(1_000)
            val foods = searchFoodUseCase("")
            uiState = uiState.copy(searchList = foods)
        }
    }

    fun dispatch(action: SearchFoodUiAction) {
        viewModelScope.launch {
            when (action) {
                is SearchFoodUiAction.SetSearch -> {
                    uiState = uiState.copy(search = action.value)
                    searchJob?.cancel()
                    searchJob = viewModelScope.launch {
                        delay(500)
                        val foods = searchFoodUseCase(action.value)
                        uiState = uiState.copy(searchList = foods)
                    }
                }
                is SearchFoodUiAction.FoodDetail -> {
                    _uiEvent.emit(SearchFoodUiEvent.FoodDetail(action.foodId))
                }
                SearchFoodUiAction.OnBackPressed -> {
                    _uiEvent.emit(SearchFoodUiEvent.OnBackPressed)
                }
            }
        }
    }
}