package com.adedom.food_detail.di

import com.adedom.food_detail.data.providers.remote.FoodRemoteDataSource
import com.adedom.food_detail.data.providers.remote.FoodRemoteDataSourceImpl
import com.adedom.food_detail.data.repositories.FoodDetailRepositoryImpl
import com.adedom.food_detail.domain.repositories.FoodDetailRepository
import com.adedom.food_detail.domain.use_cases.GetFoodDetailUseCase
import com.adedom.food_detail.presentation.view_model.FoodDetailViewModel
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val featureFoodDetailModule = DI.Module(name = "featureFoodDetailModule") {

    // data
    bindSingleton<FoodRemoteDataSource> { FoodRemoteDataSourceImpl(instance(), instance()) }

    bindSingleton<FoodDetailRepository> { FoodDetailRepositoryImpl(instance()) }

    // domain
    bindProvider { GetFoodDetailUseCase(instance()) }

    // view model
    bindProvider { FoodDetailViewModel(instance()) }
}