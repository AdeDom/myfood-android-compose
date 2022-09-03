package com.adedom.myfood.di

import com.adedom.search_food.data.providers.local.food.FoodLocalDataSource
import com.adedom.search_food.data.providers.local.food.FoodLocalDataSourceImpl
import com.adedom.search_food.data.repositories.SearchFoodRepositoryImpl
import com.adedom.search_food.domain.repositories.SearchFoodRepository
import com.adedom.search_food.domain.use_cases.SearchFoodUseCase
import com.adedom.search_food.presentation.view_model.SearchFoodViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val featureSearchFoodModule = module {

    // data
    single<FoodLocalDataSource> { FoodLocalDataSourceImpl(get()) }

    single<SearchFoodRepository> { SearchFoodRepositoryImpl(get()) }

    // domain
    factory { SearchFoodUseCase(get()) }

    // presentation
    viewModel { SearchFoodViewModel(get()) }
}