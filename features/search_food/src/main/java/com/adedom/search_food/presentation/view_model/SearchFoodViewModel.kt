package com.adedom.search_food.presentation.view_model

import com.adedom.core.domain.models.FoodModel
import com.adedom.search_food.domain.use_cases.SearchFoodUseCase
import com.adedom.ui_components.base.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class SearchFoodUiState(
    val initial: Unit? = null,
    val search: String = "",
    val searchList: List<FoodModel> = emptyList(),
    val isEmptyData: Boolean = false,
)

sealed interface SearchFoodUiEvent {
    data class FoodDetail(val foodId: Long) : SearchFoodUiEvent
    object OnBackPressed : SearchFoodUiEvent
}

sealed interface SearchFoodUiAction {
    object Initial : SearchFoodUiAction
    data class SetSearch(val value: String) : SearchFoodUiAction
    data class FoodDetail(val foodId: Long) : SearchFoodUiAction
    object OnBackPressed : SearchFoodUiAction
}

class SearchFoodViewModel(
    private val searchFoodUseCase: SearchFoodUseCase,
) : BaseViewModel<SearchFoodUiState, SearchFoodUiEvent, SearchFoodUiAction>(SearchFoodUiState()) {

    private var searchJob: Job? = null

    override fun dispatch(action: SearchFoodUiAction) {
        launch {
            when (action) {
                SearchFoodUiAction.Initial -> {
                    delay(200)
                    val foods = searchFoodUseCase("")
                    setState {
                        copy(
                            searchList = foods,
                            isEmptyData = foods.isEmpty(),
                        )
                    }
                }
                is SearchFoodUiAction.SetSearch -> {
                    setState { copy(search = action.value) }
                    searchJob?.cancel()
                    searchJob = launch {
                        delay(500)
                        val foods = searchFoodUseCase(action.value)
                        setState {
                            copy(
                                searchList = foods,
                                isEmptyData = foods.isEmpty(),
                            )
                        }
                    }
                }
                is SearchFoodUiAction.FoodDetail -> {
                    setState { copy(initial = Unit) }
                    setEvent(SearchFoodUiEvent.FoodDetail(action.foodId))
                }
                SearchFoodUiAction.OnBackPressed -> {
                    setEvent(SearchFoodUiEvent.OnBackPressed)
                }
            }
        }
    }
}