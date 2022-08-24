package com.adedom.authentication.di

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
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.instance

val featureAuthenticationModule = DI.Module(name = "featureAuthenticationModule") {

    // data
    bindProvider<UserProfileLocalDataSource> { UserProfileLocalDataSourceImpl(instance()) }

    bindProvider<AuthRemoteDataSource> { AuthRemoteDataSourceImpl(instance(), instance()) }
    bindProvider<ProfileRemoteDataSource> {
        ProfileRemoteDataSourceImpl(
            instance(),
            instance(),
            instance(),
        )
    }

    bindProvider<UserProfileRepository> { UserProfileRepositoryImpl(instance(), instance()) }
    bindProvider<AuthLoginRepository> { AuthLoginRepositoryImpl(instance(), instance()) }

    // domain
    bindProvider { ValidateEmailUseCase() }
    bindProvider { ValidatePasswordUseCase() }
    bindProvider { LoginUseCase(instance(), instance()) }

    // presentation
    bindProvider { LoginViewModel(instance(), instance(), instance()) }
    bindProvider { RegisterViewModel() }
}