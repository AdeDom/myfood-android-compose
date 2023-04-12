package com.adedom.myfood.di.features

import com.adedom.search_food.domain.use_cases.SearchFoodUseCase
import com.adedom.search_food.presentation.view_model.SearchFoodViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val featureSearchFoodModule = module {

    // domain
    factoryOf(::SearchFoodUseCase)

    // presentation
    viewModelOf(::SearchFoodViewModel)
}