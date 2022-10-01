package com.adedom.myfood

import android.app.Application
import com.adedom.myfood.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            modules(
                coreModule,
                dataProfileModule,
                domainUserProfileModule,
                featureAuthenticationModule,
                featureConnectivityModule,
                featureFoodDetailModule,
                featureMainModule,
                featureSearchFoodModule,
                featureSplashScreenModule,
                featureUserProfileModule,
                featureWelcomeModule,
                myFoodModule,
            )
        }
    }
}