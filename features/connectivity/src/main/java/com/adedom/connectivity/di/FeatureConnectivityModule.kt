package com.adedom.connectivity.di

import com.adedom.connectivity.data.providers.connectivity.ConnectivityObserver
import com.adedom.connectivity.data.providers.connectivity.NetworkConnectivityObserver
import com.adedom.connectivity.data.repositories.ConnectivityRepositoryImpl
import com.adedom.connectivity.domain.repositories.ConnectivityRepository
import com.adedom.connectivity.domain.use_cases.GetConnectivityStatusUseCase
import com.adedom.connectivity.presentation.view_model.ConnectivityViewModel
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val featureConnectivityModule = DI.Module(name = "featureConnectivityModule") {

    // data
    bindSingleton<ConnectivityObserver> { NetworkConnectivityObserver(instance()) }

    bindSingleton<ConnectivityRepository> { ConnectivityRepositoryImpl(instance()) }

    // domain
    bindProvider { GetConnectivityStatusUseCase(instance()) }

    // view model
    bindProvider { ConnectivityViewModel(instance()) }
}