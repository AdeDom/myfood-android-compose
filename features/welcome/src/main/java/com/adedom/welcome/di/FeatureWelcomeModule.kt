package com.adedom.welcome.di

import com.adedom.welcome.data.repositories.WelcomeRepositoryImpl
import com.adedom.welcome.domain.repositories.WelcomeRepository
import com.adedom.welcome.domain.use_cases.WelcomeGuestRoleUseCase
import com.adedom.welcome.presentation.view_model.WelcomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val featureWelcomeModule = module {

    // data
    factory<WelcomeRepository> { WelcomeRepositoryImpl(get()) }

    // domain
    factory { WelcomeGuestRoleUseCase(get()) }

    // presentation
    viewModel { WelcomeViewModel(get()) }
}