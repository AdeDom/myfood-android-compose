package com.adedom.myfood.di

import android.content.Context
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import com.adedom.core.data.providers.data_store.AppDataStore
import com.adedom.myfood.data.providers.data_store.AppDataStoreImpl
import com.adedom.myfood.data.providers.database.MyFoodDatabaseDriverFactory
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val appModule = DI.Module(name = "app") {

    bindSingleton {
        PreferenceDataStoreFactory.create {
            instance<Context>().preferencesDataStoreFile("file")
        }
    }
    bindSingleton<AppDataStore> { AppDataStoreImpl(instance()) }
    bindSingleton { MyFoodDatabaseDriverFactory(instance()) }
    bindSingleton { instance<MyFoodDatabaseDriverFactory>().createDriver() }
}