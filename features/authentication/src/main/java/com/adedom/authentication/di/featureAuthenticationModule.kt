package com.adedom.authentication.di

import com.adedom.authentication.domain.use_cases.ValidateEmailUseCase
import com.adedom.authentication.domain.use_cases.ValidatePasswordUseCase
import com.adedom.authentication.presentation.view_model.LoginViewModel
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.instance

val featureAuthenticationModule = DI.Module(name = "featureAuthenticationModule") {

    // data

    // domain
    bindProvider { ValidateEmailUseCase() }
    bindProvider { ValidatePasswordUseCase() }

    // presentation
    bindProvider { LoginViewModel(instance(), instance()) }
}