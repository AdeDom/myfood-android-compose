package com.adedom.myfood.di.data

import com.adedom.profile.providers.local.UserProfileLocalDataSource
import com.adedom.profile.providers.local.UserProfileLocalDataSourceImpl
import com.adedom.profile.providers.remote.ProfileRemoteDataSource
import com.adedom.profile.providers.remote.ProfileRemoteDataSourceImpl
import com.adedom.profile.repositories.UserProfileRepository
import com.adedom.profile.repositories.UserProfileRepositoryImpl
import org.koin.dsl.module

val dataProfileModule = module {

    // local
    single<UserProfileLocalDataSource> { UserProfileLocalDataSourceImpl(get()) }

    // remote
    single<ProfileRemoteDataSource> { ProfileRemoteDataSourceImpl(get(), get()) }

    // repository
    single<UserProfileRepository> { UserProfileRepositoryImpl(get(), get()) }
}