package com.adedom.splash_screen.di

import com.adedom.splash_screen.data.repositories.SplashScreenRepositoryImpl
import com.adedom.splash_screen.domain.repositories.SplashScreenRepository
import com.adedom.splash_screen.domain.use_cases.GetIsAuthUseCase
import com.adedom.splash_screen.presentation.view_model.SplashScreenViewModel
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.instance

val featureSplashScreenModule = DI.Module(name = "featureSplashScreenModule") {

    // data
    bindProvider<SplashScreenRepository> { SplashScreenRepositoryImpl(instance()) }

    // domain
    bindProvider { GetIsAuthUseCase(instance()) }

    // presentation
    bindProvider { SplashScreenViewModel(instance()) }
}