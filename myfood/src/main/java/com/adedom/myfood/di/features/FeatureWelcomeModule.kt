package com.adedom.myfood.di.features

import com.adedom.welcome.data.repositories.WelcomeRepositoryImpl
import com.adedom.welcome.domain.repositories.WelcomeRepository
import com.adedom.welcome.domain.use_cases.WelcomeGuestRoleUseCase
import com.adedom.welcome.presentation.view_model.WelcomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val featureWelcomeModule = module {

    // data
    factoryOf(::WelcomeRepositoryImpl) { bind<WelcomeRepository>() }

    // domain
    factoryOf(::WelcomeGuestRoleUseCase)

    // presentation
    viewModelOf(::WelcomeViewModel)
}