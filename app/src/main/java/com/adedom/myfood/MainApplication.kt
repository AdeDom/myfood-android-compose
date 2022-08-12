package com.adedom.myfood

import android.app.Application
import com.adedom.authentication.di.featureAuthenticationModule
import com.adedom.data.di.dataModule
import com.adedom.domain.di.domainModule
import com.adedom.myfood.di.appModule
import com.adedom.myfood.di.presentationModule
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
            dataModule,
            domainModule,
            presentationModule,
            featureSplashScreenModule,
            featureWelcomeModule,
            featureAuthenticationModule,
        )
    }
}