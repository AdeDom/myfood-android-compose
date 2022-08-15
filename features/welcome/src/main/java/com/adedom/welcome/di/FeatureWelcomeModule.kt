package com.adedom.welcome.di

import com.adedom.welcome.data.repositories.WelcomeRepositoryImpl
import com.adedom.welcome.domain.repositories.WelcomeRepository
import com.adedom.welcome.domain.use_cases.WelcomeGuestRoleUseCase
import com.adedom.welcome.presentation.view_model.WelcomeViewModel
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.instance

val featureWelcomeModule = DI.Module(name = "featureWelcomeModule") {

    // data
    bindProvider<WelcomeRepository> { WelcomeRepositoryImpl(instance()) }

    // domain
    bindProvider { WelcomeGuestRoleUseCase(instance()) }

    // presentation
    bindProvider { WelcomeViewModel(instance()) }
}