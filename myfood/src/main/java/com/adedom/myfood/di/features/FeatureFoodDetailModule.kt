package com.adedom.myfood.di.features

import com.adedom.food_detail.domain.use_cases.GetFavoriteFlowUseCase
import com.adedom.food_detail.domain.use_cases.GetFoodDetailUseCase
import com.adedom.food_detail.domain.use_cases.InsertOrReplaceFavoriteUseCase
import com.adedom.food_detail.domain.use_cases.UpdateBackupFavoriteUseCase
import com.adedom.food_detail.presentation.view_model.FoodDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val featureFoodDetailModule = module {

    // domain
    factory { GetFoodDetailUseCase(get(), get(), get()) }
    factory { InsertOrReplaceFavoriteUseCase(get(), get()) }
    factory { GetFavoriteFlowUseCase(get()) }
    factory { UpdateBackupFavoriteUseCase(get()) }

    // view model
    viewModel { FoodDetailViewModel(get(), get(), get(), get(), get(), get(), get()) }
}