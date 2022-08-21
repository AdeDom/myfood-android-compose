package com.adedom.search_food.di

import com.adedom.search_food.data.providers.local.food.FoodLocalDataSource
import com.adedom.search_food.data.providers.local.food.FoodLocalDataSourceImpl
import com.adedom.search_food.data.repositories.SearchFoodRepositoryImpl
import com.adedom.search_food.domain.repositories.SearchFoodRepository
import com.adedom.search_food.domain.use_cases.SearchFoodUseCase
import com.adedom.search_food.presentation.view_model.SearchFoodViewModel
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val featureSearchFoodModule = DI.Module(name = "featureSearchFoodModule") {

    // data
    bindSingleton<FoodLocalDataSource> { FoodLocalDataSourceImpl(instance()) }

    bindSingleton<SearchFoodRepository> { SearchFoodRepositoryImpl(instance()) }

    // domain
    bindProvider { SearchFoodUseCase(instance()) }

    // presentation
    bindProvider { SearchFoodViewModel(instance()) }
}