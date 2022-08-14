package com.adedom.connectivity.di

import com.adedom.connectivity.connectivity.ConnectivityObserver
import com.adedom.connectivity.connectivity.NetworkConnectivityObserver
import com.adedom.connectivity.view_model.ConnectivityViewModel
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val featureConnectivityModule = DI.Module(name = "featureConnectivityModule") {

    // connectivity
    bindSingleton<ConnectivityObserver> { NetworkConnectivityObserver(instance()) }

    // view model
    bindProvider { ConnectivityViewModel(instance()) }
}