package com.adedom.myfood.di.data

import com.adedom.data.providers.local.FoodLocalDataSource
import com.adedom.data.providers.local.FoodLocalDataSourceImpl
import com.adedom.data.providers.remote.FoodRemoteDataSource
import com.adedom.data.providers.remote.FoodRemoteDataSourceImpl
import com.adedom.data.repositories.FoodRepository
import com.adedom.data.repositories.FoodRepositoryImpl
import org.koin.dsl.module

val dataFoodModule = module {

    // local data source
    single<FoodLocalDataSource> { FoodLocalDataSourceImpl(get()) }

    // remote data source
    single<FoodRemoteDataSource> { FoodRemoteDataSourceImpl(get()) }

    // repository
    single<FoodRepository> { FoodRepositoryImpl(get(), get()) }
}