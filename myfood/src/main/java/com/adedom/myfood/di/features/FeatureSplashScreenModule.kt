package com.adedom.myfood.di.features

import com.adedom.splash_screen.data.repositories.SplashScreenRepositoryImpl
import com.adedom.splash_screen.domain.repositories.SplashScreenRepository
import com.adedom.splash_screen.domain.use_cases.GetIsAuthUseCase
import com.adedom.splash_screen.presentation.view_model.SplashScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val featureSplashScreenModule = module {

    // data
    factory<SplashScreenRepository> { SplashScreenRepositoryImpl(get()) }

    // domain
    factory { GetIsAuthUseCase(get()) }

    // presentation
    viewModel { SplashScreenViewModel(get()) }
}