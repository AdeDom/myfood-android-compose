package com.adedom.myfood.di.data

import com.adedom.data.repositories.WebSocketRepository
import com.adedom.data.repositories.WebSocketRepositoryImpl
import org.koin.dsl.module

val dataWebSocketModule = module {

    // repository
    single<WebSocketRepository> { WebSocketRepositoryImpl() }
}