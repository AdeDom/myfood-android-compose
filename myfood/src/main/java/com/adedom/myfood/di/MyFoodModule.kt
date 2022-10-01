package com.adedom.myfood.di

import android.content.Context
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import com.adedom.core.data.providers.data_store.AppDataStore
import com.adedom.myfood.data.providers.data_store.AppDataStoreImpl
import com.adedom.myfood.data.providers.database.MyFoodDatabaseDriverFactory
import org.koin.dsl.module

val myFoodModule = module {

    single {
        PreferenceDataStoreFactory.create {
            get<Context>().preferencesDataStoreFile("file")
        }
    }
    single<AppDataStore> { AppDataStoreImpl(get()) }
    single { MyFoodDatabaseDriverFactory(get()) }
    single { get<MyFoodDatabaseDriverFactory>().createDriver() }
}