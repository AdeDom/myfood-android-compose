package com.adedom.myfood.di.features

import com.adedom.authentication.data.providers.remote.auth.AuthRemoteDataSource
import com.adedom.authentication.data.providers.remote.auth.AuthRemoteDataSourceImpl
import com.adedom.authentication.data.repositories.AuthRepositoryImpl
import com.adedom.authentication.domain.repositories.AuthRepository
import com.adedom.authentication.domain.use_cases.FavoriteUseCase
import com.adedom.authentication.domain.use_cases.LoginUseCase
import com.adedom.authentication.domain.use_cases.RegisterUseCase
import com.adedom.authentication.presentation.view_model.LoginViewModel
import com.adedom.authentication.presentation.view_model.RegisterViewModel
import com.myfood.server.usecase.validate.ValidateEmailUseCase
import com.myfood.server.usecase.validate.ValidatePasswordUseCase
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val featureAuthenticationModule = module {

    // data
    factoryOf(::AuthRemoteDataSourceImpl) { bind<AuthRemoteDataSource>() }

    factoryOf(::AuthRepositoryImpl) { bind<AuthRepository>() }

    // domain
    factoryOf(::ValidateEmailUseCase)
    factoryOf(::ValidatePasswordUseCase)
    factoryOf(::LoginUseCase)
    factoryOf(::RegisterUseCase)
    factoryOf(::FavoriteUseCase)

    // presentation
    viewModelOf(::LoginViewModel)
    viewModelOf(::RegisterViewModel)
}