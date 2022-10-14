package com.adedom.myfood.di.data

import com.adedom.data.providers.remote.FavoriteRemoteDataSource
import com.adedom.data.providers.remote.FavoriteRemoteDataSourceImpl
import com.adedom.data.repositories.FavoriteRepository
import com.adedom.data.repositories.FavoriteRepositoryImpl
import org.koin.dsl.module

val dataFavoriteModule = module {

    // local data source

    // remote data source
    single<FavoriteRemoteDataSource> { FavoriteRemoteDataSourceImpl(get()) }

    // repository
    single<FavoriteRepository> { FavoriteRepositoryImpl(get()) }
}