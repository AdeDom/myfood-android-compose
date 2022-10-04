package com.adedom.myfood.di.features

import com.adedom.food_detail.data.providers.remote.FoodRemoteDataSource
import com.adedom.food_detail.data.providers.remote.FoodRemoteDataSourceImpl
import com.adedom.food_detail.data.repositories.FoodDetailRepositoryImpl
import com.adedom.food_detail.domain.repositories.FoodDetailRepository
import com.adedom.food_detail.domain.use_cases.GetFoodDetailUseCase
import com.adedom.food_detail.presentation.view_model.FoodDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val featureFoodDetailModule = module {

    // data
    single<FoodRemoteDataSource> { FoodRemoteDataSourceImpl(get()) }

    single<FoodDetailRepository> { FoodDetailRepositoryImpl(get()) }

    // domain
    factory { GetFoodDetailUseCase(get()) }

    // view model
    viewModel { FoodDetailViewModel(get(), get(), get(), get()) }
}