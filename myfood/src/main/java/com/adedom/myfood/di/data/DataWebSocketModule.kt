package com.adedom.myfood.di.data

import com.adedom.data.providers.web_sockets.FavoriteWebSocketDataSource
import com.adedom.data.providers.web_sockets.FavoriteWebSocketDataSourceImpl
import com.adedom.data.repositories.WebSocketRepository
import com.adedom.data.repositories.WebSocketRepositoryImpl
import org.koin.dsl.module

val dataWebSocketModule = module {

    // web socket data source
    single<FavoriteWebSocketDataSource> { FavoriteWebSocketDataSourceImpl(get(), get()) }

    // repository
    single<WebSocketRepository> { WebSocketRepositoryImpl() }
}