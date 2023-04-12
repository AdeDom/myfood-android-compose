package com.adedom.myfood.di.data

import com.adedom.data.providers.local.FavoriteLocalDataSource
import com.adedom.data.providers.local.FavoriteLocalDataSourceImpl
import com.adedom.data.providers.remote.FavoriteRemoteDataSource
import com.adedom.data.providers.remote.FavoriteRemoteDataSourceImpl
import com.adedom.data.repositories.FavoriteRepository
import com.adedom.data.repositories.FavoriteRepositoryImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataFavoriteModule = module {

    // local data source
    singleOf(::FavoriteLocalDataSourceImpl) { bind<FavoriteLocalDataSource>() }

    // remote data source
    singleOf(::FavoriteRemoteDataSourceImpl) { bind<FavoriteRemoteDataSource>() }

    // repository
    singleOf(::FavoriteRepositoryImpl) { bind<FavoriteRepository>() }
}