package com.adedom.search_food.presentation.view_model

import androidx.lifecycle.viewModelScope
import com.adedom.search_food.domain.use_cases.SearchFoodUseCase
import com.adedom.ui.components.base.BaseViewModel
import com.adedom.ui.components.domain.models.FoodModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class SearchFoodUiState(
    val initial: Unit? = null,
    val search: String = "",
    val searchList: List<FoodModel> = emptyList(),
)

sealed interface SearchFoodUiEvent {
    object Initial : SearchFoodUiEvent
    data class SetSearch(val value: String) : SearchFoodUiEvent
}

class SearchFoodViewModel(
    private val searchFoodUseCase: SearchFoodUseCase,
) : BaseViewModel<SearchFoodUiEvent, SearchFoodUiState>(SearchFoodUiState()) {

    private var searchJob: Job? = null

    override fun onEvent(event: SearchFoodUiEvent) {
        viewModelScope.launch {
            when (event) {
                SearchFoodUiEvent.Initial -> {
                    delay(200)
                    val foods = searchFoodUseCase("")
                    emit {
                        copy(
                            initial = Unit,
                            searchList = foods,
                        )
                    }
                }
                is SearchFoodUiEvent.SetSearch -> {
                    emit { copy(search = event.value) }
                    searchJob?.cancel()
                    searchJob = launch {
                        delay(500)
                        val foods = searchFoodUseCase(event.value)
                        emit { copy(searchList = foods) }
                    }
                }
            }
        }
    }
}