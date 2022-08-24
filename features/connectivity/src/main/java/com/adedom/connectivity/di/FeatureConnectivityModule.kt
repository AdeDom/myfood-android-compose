package com.adedom.connectivity.di

import com.adedom.connectivity.data.providers.connectivity.ConnectivityObserver
import com.adedom.connectivity.data.providers.connectivity.NetworkConnectivityObserver
import com.adedom.connectivity.data.repositories.ConnectivityRepositoryImpl
import com.adedom.connectivity.domain.repositories.ConnectivityRepository
import com.adedom.connectivity.domain.use_cases.GetConnectivityStatusUseCase
import com.adedom.connectivity.presentation.view_model.ConnectivityViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val featureConnectivityModule = module {

    // data
    single<ConnectivityObserver> { NetworkConnectivityObserver(get()) }

    single<ConnectivityRepository> { ConnectivityRepositoryImpl(get()) }

    // domain
    factory { GetConnectivityStatusUseCase(get()) }

    // view model
    viewModel { ConnectivityViewModel(get()) }
}