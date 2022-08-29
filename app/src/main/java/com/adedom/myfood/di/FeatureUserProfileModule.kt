package com.adedom.myfood.di

import com.adedom.user_profile.data.providers.local.user_profile.UserProfileLocalDataSource
import com.adedom.user_profile.data.providers.local.user_profile.UserProfileLocalDataSourceImpl
import com.adedom.user_profile.data.providers.remote.profile.ProfileRemoteDataSource
import com.adedom.user_profile.data.providers.remote.profile.ProfileRemoteDataSourceImpl
import com.adedom.user_profile.data.repositories.UserProfileRepositoryImpl
import com.adedom.user_profile.domain.repositories.UserProfileRepository
import com.adedom.user_profile.domain.use_cases.FetchUserProfileUseCase
import com.adedom.user_profile.domain.use_cases.GetUserProfileUseCase
import com.adedom.user_profile.presentation.view_model.UserProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val featureUserProfileModule = module {

    // data
    single<UserProfileLocalDataSource> { UserProfileLocalDataSourceImpl(get()) }

    single<ProfileRemoteDataSource> { ProfileRemoteDataSourceImpl(get(), get()) }

    single<UserProfileRepository> { UserProfileRepositoryImpl(get(), get()) }

    // domain
    factory { FetchUserProfileUseCase(get()) }
    factory { GetUserProfileUseCase(get()) }

    // presentation
    viewModel { UserProfileViewModel(get(), get()) }
}