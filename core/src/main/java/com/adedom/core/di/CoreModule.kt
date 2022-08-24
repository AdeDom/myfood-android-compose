package com.adedom.core.di

import com.adedom.core.data.providers.remote.DataSourceProvider
import com.adedom.myfood.MyFoodDatabase
import io.ktor.client.engine.cio.*
import org.koin.dsl.module

val coreModule = module {

    single { CIO.create() }
    single { DataSourceProvider(get()) }
    single { MyFoodDatabase(get()) }
}