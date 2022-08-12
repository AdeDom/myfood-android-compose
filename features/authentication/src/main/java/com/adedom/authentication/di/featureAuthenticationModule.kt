package com.adedom.authentication.di

import com.adedom.authentication.presentation.view_model.LoginViewModel
import org.kodein.di.DI
import org.kodein.di.bindProvider

val featureAuthenticationModule = DI.Module(name = "featureAuthenticationModule") {

    // data

    // domain

    // presentation
    bindProvider { LoginViewModel() }
}