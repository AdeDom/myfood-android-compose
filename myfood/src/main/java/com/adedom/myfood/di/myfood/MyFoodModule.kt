package com.adedom.myfood.di.myfood

import android.content.Context
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import com.adedom.core.data.providers.datastore.AppDataStore
import com.adedom.myfood.data.providers.data_store.AppDataStoreImpl
import com.adedom.myfood.data.providers.database.MyFoodDatabaseDriverFactory
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val myFoodModule = module {

    single {
        PreferenceDataStoreFactory.create {
            get<Context>().preferencesDataStoreFile(AppDataStore.fileName)
        }
    }
    singleOf(::AppDataStoreImpl) { bind<AppDataStore>() }
    singleOf(::MyFoodDatabaseDriverFactory)
    single { get<MyFoodDatabaseDriverFactory>().createDriver() }
}