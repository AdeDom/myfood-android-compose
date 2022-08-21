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
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.androidXModule

class MainApplication : Application(), DIAware {

    override val di by DI.lazy {
        import(androidXModule(this@MainApplication))

        importAll(
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