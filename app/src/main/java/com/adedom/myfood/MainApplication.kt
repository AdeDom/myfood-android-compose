package com.adedom.myfood

import android.app.Application
import com.adedom.authentication.di.featureAuthenticationModule
import com.adedom.connectivity.di.featureConnectivityModule
import com.adedom.core.di.coreModule
import com.adedom.food_detail.di.featureFoodDetailModule
import com.adedom.main.di.featureMainModule
import com.adedom.myfood.di.appModule
import com.adedom.search_food.di.featureSearchFoodModule
import com.adedom.splash_screen.di.featureSplashScreenModule
import com.adedom.welcome.di.featureWelcomeModule
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
                appModule,
                coreModule,
                featureSplashScreenModule,
                featureWelcomeModule,
                featureAuthenticationModule,
                featureMainModule,
                featureConnectivityModule,
                featureFoodDetailModule,
                featureSearchFoodModule,
            )
        }
    }
}