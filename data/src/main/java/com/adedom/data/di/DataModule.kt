package com.adedom.data.di

import com.adedom.data.providers.local.UserProfileLocalDataSource
import com.adedom.data.providers.local.UserProfileLocalDataSourceImpl
import com.adedom.data.providers.remote.auth.AuthRemoteDataSource
import com.adedom.data.providers.remote.auth.AuthRemoteDataSourceImpl
import com.adedom.data.providers.remote.category.CategoryRemoteDataSource
import com.adedom.data.providers.remote.category.CategoryRemoteDataSourceImpl
import com.adedom.data.providers.remote.food.FoodRemoteDataSource
import com.adedom.data.providers.remote.food.FoodRemoteDataSourceImpl
import com.adedom.data.providers.remote.profile.ProfileRemoteDataSource
import com.adedom.data.providers.remote.profile.ProfileRemoteDataSourceImpl
import com.adedom.data.repositories.auth.AuthLoginRepository
import com.adedom.data.repositories.auth.AuthLoginRepositoryImpl
import com.adedom.data.repositories.auth.AuthLogoutRepository
import com.adedom.data.repositories.auth.AuthLogoutRepositoryImpl
import com.adedom.data.repositories.home.HomeRepository
import com.adedom.data.repositories.home.HomeRepositoryImpl
import com.adedom.data.repositories.profile.UserProfileRepository
import com.adedom.data.repositories.profile.UserProfileRepositoryImpl
import com.adedom.data.repositories.splash_screen.SplashScreenRepository
import com.adedom.data.repositories.splash_screen.SplashScreenRepositoryImpl
import com.adedom.data.repositories.welcome.WelcomeRepository
import com.adedom.data.repositories.welcome.WelcomeRepositoryImpl
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val dataModule = DI.Module(name = "data") {

    bindSingleton<UserProfileLocalDataSource> { UserProfileLocalDataSourceImpl(instance()) }

    bindSingleton<AuthRemoteDataSource> {
        AuthRemoteDataSourceImpl(
            instance(),
            instance(),
            instance(),
        )
    }
    bindSingleton<ProfileRemoteDataSource> {
        ProfileRemoteDataSourceImpl(
            instance(),
            instance(),
            instance(),
        )
    }
    bindSingleton<CategoryRemoteDataSource> { CategoryRemoteDataSourceImpl(instance(), instance()) }
    bindSingleton<FoodRemoteDataSource> { FoodRemoteDataSourceImpl(instance(), instance()) }

    bindSingleton<SplashScreenRepository> { SplashScreenRepositoryImpl(instance()) }
    bindSingleton<AuthLoginRepository> { AuthLoginRepositoryImpl(instance(), instance()) }
    bindSingleton<AuthLogoutRepository> { AuthLogoutRepositoryImpl(instance(), instance()) }
    bindSingleton<UserProfileRepository> { UserProfileRepositoryImpl(instance(), instance()) }
    bindSingleton<WelcomeRepository> { WelcomeRepositoryImpl(instance()) }
    bindSingleton<HomeRepository> { HomeRepositoryImpl(instance(), instance()) }
}