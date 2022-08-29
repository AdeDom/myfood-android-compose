package com.adedom.myfood.di

import com.adedom.core.data.providers.remote.DataProviderRemote
import com.adedom.myfood.MyFoodDatabase
import org.koin.dsl.module

val coreModule = module {

    single { DataProviderRemote(get()) }
    single { MyFoodDatabase(get()) }
}