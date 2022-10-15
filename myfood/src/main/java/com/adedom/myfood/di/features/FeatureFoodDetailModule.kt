package com.adedom.myfood.di.features

import com.adedom.food_detail.domain.use_cases.GetFavoriteFlowUseCase
import com.adedom.food_detail.domain.use_cases.GetFoodDetailUseCase
import com.adedom.food_detail.domain.use_cases.InsertFavoriteUseCase
import com.adedom.food_detail.presentation.view_model.FoodDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val featureFoodDetailModule = module {

    // domain
    factory { GetFoodDetailUseCase(get(), get(), get()) }
    factory { InsertFavoriteUseCase(get(), get()) }
    factory { GetFavoriteFlowUseCase(get()) }

    // view model
    viewModel { FoodDetailViewModel(get(), get(), get(), get(), get(), get()) }
}