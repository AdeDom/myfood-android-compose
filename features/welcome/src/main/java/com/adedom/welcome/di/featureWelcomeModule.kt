package com.adedom.welcome.di

import com.adedom.welcome.presentation.view_model.WelcomeViewModel
import org.kodein.di.DI
import org.kodein.di.bindProvider

val featureWelcomeModule = DI.Module(name = "featureWelcomeModule") {

    // data

    // domain

    // presentation
    bindProvider { WelcomeViewModel() }
}