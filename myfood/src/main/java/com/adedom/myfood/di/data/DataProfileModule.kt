package com.adedom.myfood.di.data

import com.adedom.profile.providers.local.UserProfileLocalDataSource
import com.adedom.profile.providers.local.UserProfileLocalDataSourceImpl
import com.adedom.profile.providers.remote.ProfileRemoteDataSource
import com.adedom.profile.providers.remote.ProfileRemoteDataSourceImpl
import com.adedom.profile.repositories.UserProfileRepository
import com.adedom.profile.repositories.UserProfileRepositoryImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataProfileModule = module {

    // local
    singleOf(::UserProfileLocalDataSourceImpl) { bind<UserProfileLocalDataSource>() }

    // remote
    singleOf(::ProfileRemoteDataSourceImpl) { bind<ProfileRemoteDataSource>() }

    // repository
    singleOf(::UserProfileRepositoryImpl) { bind<UserProfileRepository>() }
}