package com.adedom.myfood

import android.app.Application
import com.adedom.myfood.di.core.coreModule
import com.adedom.myfood.di.data.dataFavoriteModule
import com.adedom.myfood.di.data.dataFoodModule
import com.adedom.myfood.di.data.dataProfileModule
import com.adedom.myfood.di.data.dataWebSocketModule
import com.adedom.myfood.di.domain.domainUserProfileModule
import com.adedom.myfood.di.domain.domainWebSocketModule
import com.adedom.myfood.di.features.*
import com.adedom.myfood.di.myfood.myFoodModule
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
                dataWebSocketModule,
                dataFoodModule,
                dataFavoriteModule,
                domainUserProfileModule,
                domainWebSocketModule,
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