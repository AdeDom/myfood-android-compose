package com.adedom.myfood.di.features

import com.adedom.food_detail.domain.use_cases.GetFavoriteFlowUseCase
import com.adedom.food_detail.domain.use_cases.GetFoodDetailUseCase
import com.adedom.food_detail.domain.use_cases.InsertOrReplaceFavoriteUseCase
import com.adedom.food_detail.domain.use_cases.UpdateBackupFavoriteUseCase
import com.adedom.food_detail.presentation.view_model.FoodDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val featureFoodDetailModule = module {

    // domain
    factoryOf(::GetFoodDetailUseCase)
    factoryOf(::InsertOrReplaceFavoriteUseCase)
    factoryOf(::GetFavoriteFlowUseCase)
    factoryOf(::UpdateBackupFavoriteUseCase)

    // view model
    viewModelOf(::FoodDetailViewModel)
}