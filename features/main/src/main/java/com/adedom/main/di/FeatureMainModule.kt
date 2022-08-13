package com.adedom.main.di

import com.adedom.main.presentation.view_model.MainViewModel
import org.kodein.di.DI
import org.kodein.di.bindProvider

val featureMainModule = DI.Module(name = "featureMainModule") {

    // data

    // domain

    //presentation
    bindProvider { MainViewModel() }
}