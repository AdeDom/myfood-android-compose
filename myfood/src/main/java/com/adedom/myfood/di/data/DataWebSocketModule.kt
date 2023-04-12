package com.adedom.myfood.di.data

import com.adedom.data.providers.web_sockets.FavoriteWebSocketDataSource
import com.adedom.data.providers.web_sockets.FavoriteWebSocketDataSourceImpl
import com.adedom.data.repositories.FavoriteWebSocketRepository
import com.adedom.data.repositories.FavoriteWebSocketRepositoryImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataWebSocketModule = module {

    // web socket data source
    singleOf(::FavoriteWebSocketDataSourceImpl) { bind<FavoriteWebSocketDataSource>() }

    // repository
    singleOf(::FavoriteWebSocketRepositoryImpl) { bind<FavoriteWebSocketRepository>() }
}