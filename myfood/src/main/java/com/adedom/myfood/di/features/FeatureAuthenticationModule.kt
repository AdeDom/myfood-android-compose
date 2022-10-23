package com.adedom.myfood.di.features

import com.adedom.authentication.data.providers.remote.auth.AuthRemoteDataSource
import com.adedom.authentication.data.providers.remote.auth.AuthRemoteDataSourceImpl
import com.adedom.authentication.data.repositories.AuthRepositoryImpl
import com.adedom.authentication.domain.repositories.AuthRepository
import com.adedom.authentication.domain.use_cases.FavoriteUseCase
import com.adedom.authentication.domain.use_cases.LoginUseCase
import com.adedom.authentication.presentation.view_model.LoginViewModel
import com.adedom.authentication.presentation.view_model.RegisterViewModel
import com.myfood.server.usecase.validate.ValidateEmailUseCase
import com.myfood.server.usecase.validate.ValidatePasswordUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val featureAuthenticationModule = module {

    // data
    factory<AuthRemoteDataSource> { AuthRemoteDataSourceImpl(get()) }

    factory<AuthRepository> { AuthRepositoryImpl(get(), get()) }

    // domain
    factory { ValidateEmailUseCase() }
    factory { ValidatePasswordUseCase() }
    factory { LoginUseCase(get()) }
    factory { FavoriteUseCase(get(), get()) }

    // presentation
    viewModel { LoginViewModel(get(), get(), get(), get(), get()) }
    viewModel { RegisterViewModel() }
}