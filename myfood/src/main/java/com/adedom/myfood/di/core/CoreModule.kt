package com.adedom.myfood.di.core

import com.adedom.core.data.providers.remote.AppHttpClientEngine
import com.adedom.core.data.providers.remote.DataProviderRemote
import com.adedom.core.data.providers.remote.HttpClientEngineCIO
import com.adedom.myfood.MyFoodDatabase
import org.koin.dsl.module

val coreModule = module {

    single<AppHttpClientEngine> { HttpClientEngineCIO() }
    single { DataProviderRemote(get(), get()) }
    single { MyFoodDatabase(get()) }
}