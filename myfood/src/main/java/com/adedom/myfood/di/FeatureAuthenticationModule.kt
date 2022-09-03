package com.adedom.myfood.di

import com.adedom.authentication.data.providers.local.user_profile.UserProfileLocalDataSource
import com.adedom.authentication.data.providers.local.user_profile.UserProfileLocalDataSourceImpl
import com.adedom.authentication.data.providers.remote.auth.AuthRemoteDataSource
import com.adedom.authentication.data.providers.remote.auth.AuthRemoteDataSourceImpl
import com.adedom.authentication.data.providers.remote.profile.ProfileRemoteDataSource
import com.adedom.authentication.data.providers.remote.profile.ProfileRemoteDataSourceImpl
import com.adedom.authentication.data.repositories.AuthLoginRepositoryImpl
import com.adedom.authentication.data.repositories.UserProfileRepositoryImpl
import com.adedom.authentication.domain.repositories.AuthLoginRepository
import com.adedom.authentication.domain.repositories.UserProfileRepository
import com.adedom.authentication.domain.use_cases.LoginUseCase
import com.adedom.authentication.domain.use_cases.ValidateEmailUseCase
import com.adedom.authentication.domain.use_cases.ValidatePasswordUseCase
import com.adedom.authentication.presentation.view_model.LoginViewModel
import com.adedom.authentication.presentation.view_model.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val featureAuthenticationModule = module {

    // data
    factory<UserProfileLocalDataSource> { UserProfileLocalDataSourceImpl(get()) }

    factory<AuthRemoteDataSource> { AuthRemoteDataSourceImpl(get()) }
    factory<ProfileRemoteDataSource> { ProfileRemoteDataSourceImpl(get(), get()) }

    factory<UserProfileRepository> { UserProfileRepositoryImpl(get(), get()) }
    factory<AuthLoginRepository> { AuthLoginRepositoryImpl(get(), get()) }

    // domain
    factory { ValidateEmailUseCase() }
    factory { ValidatePasswordUseCase() }
    factory { LoginUseCase(get(), get()) }

    // presentation
    viewModel { LoginViewModel(get(), get(), get()) }
    viewModel { RegisterViewModel() }
}