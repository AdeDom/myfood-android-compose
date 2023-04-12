package com.adedom.myfood.di.features

import com.adedom.connectivity.data.providers.connectivity.ConnectivityObserver
import com.adedom.connectivity.data.providers.connectivity.NetworkConnectivityObserver
import com.adedom.connectivity.data.repositories.ConnectivityRepositoryImpl
import com.adedom.connectivity.domain.repositories.ConnectivityRepository
import com.adedom.connectivity.domain.use_cases.GetConnectivityStatusUseCase
import com.adedom.connectivity.presentation.view_model.ConnectivityViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val featureConnectivityModule = module {

    // data
    singleOf(::NetworkConnectivityObserver) { bind<ConnectivityObserver>() }

    singleOf(::ConnectivityRepositoryImpl) { bind<ConnectivityRepository>() }

    // domain
    factoryOf(::GetConnectivityStatusUseCase)

    // view model
    viewModelOf(::ConnectivityViewModel)
}