package com.adedom.core.di

import com.adedom.core.data.providers.remote.DataSourceProvider
import com.adedom.myfood.MyFoodDatabase
import io.ktor.client.engine.cio.*
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val coreModule = DI.Module(name = "coreModule") {

    bindSingleton { CIO.create() }
    bindSingleton { DataSourceProvider(instance()) }
    bindSingleton { MyFoodDatabase(instance()) }
}