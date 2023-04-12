package com.adedom.myfood.di.data

import com.adedom.data.providers.local.FoodLocalDataSource
import com.adedom.data.providers.local.FoodLocalDataSourceImpl
import com.adedom.data.providers.remote.FoodRemoteDataSource
import com.adedom.data.providers.remote.FoodRemoteDataSourceImpl
import com.adedom.data.repositories.FoodRepository
import com.adedom.data.repositories.FoodRepositoryImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataFoodModule = module {

    // local data source
    singleOf(::FoodLocalDataSourceImpl) { bind<FoodLocalDataSource>() }

    // remote data source
    singleOf(::FoodRemoteDataSourceImpl) { bind<FoodRemoteDataSource>() }

    // repository
    singleOf(::FoodRepositoryImpl) { bind<FoodRepository>() }
}