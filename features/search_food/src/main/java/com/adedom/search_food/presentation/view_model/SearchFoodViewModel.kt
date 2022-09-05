package com.adedom.search_food.presentation.view_model

import com.adedom.core.domain.models.FoodModel
import com.adedom.search_food.domain.use_cases.SearchFoodUseCase
import com.adedom.ui_components.base.BaseMvi
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class SearchFoodUiState(
    val initial: Unit? = null,
    val search: String = "",
    val searchList: List<FoodModel> = emptyList(),
)

sealed interface SearchFoodUiAction {
    object Initial : SearchFoodUiAction
    data class SetSearch(val value: String) : SearchFoodUiAction
}

class SearchFoodViewModel(
    private val searchFoodUseCase: SearchFoodUseCase,
) : BaseMvi<SearchFoodUiState, SearchFoodUiAction>(SearchFoodUiState()) {

    private var searchJob: Job? = null

    override fun dispatch(action: SearchFoodUiAction) {
        launch {
            when (action) {
                SearchFoodUiAction.Initial -> {
                    delay(200)
                    val foods = searchFoodUseCase("")
                    setState {
                        copy(
                            initial = Unit,
                            searchList = foods,
                        )
                    }
                }
                is SearchFoodUiAction.SetSearch -> {
                    setState { copy(search = action.value) }
                    searchJob?.cancel()
                    searchJob = launch {
                        delay(500)
                        val foods = searchFoodUseCase(action.value)
                        setState { copy(searchList = foods) }
                    }
                }
            }
        }
    }
}