package com.adedom.myfood.di.core

import com.adedom.core.data.providers.remote.AppHttpClientEngine
import com.adedom.core.data.providers.remote.DataProviderRemote
import com.adedom.core.data.providers.remote.HttpClientEngineCIO
import com.adedom.myfood.MyFoodDatabase
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val coreModule = module {

    singleOf(::HttpClientEngineCIO) { bind<AppHttpClientEngine>() }
    singleOf(::DataProviderRemote)
    single { MyFoodDatabase(get()) }
}