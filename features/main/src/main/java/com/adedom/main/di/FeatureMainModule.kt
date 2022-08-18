package com.adedom.main.di

import com.adedom.main.data.providers.local.category.CategoryLocalDataSource
import com.adedom.main.data.providers.local.category.CategoryLocalDataSourceImpl
import com.adedom.main.data.providers.local.user_profile.UserProfileLocalDataSource
import com.adedom.main.data.providers.local.user_profile.UserProfileLocalDataSourceImpl
import com.adedom.main.data.providers.remote.auth.AuthRemoteDataSource
import com.adedom.main.data.providers.remote.auth.AuthRemoteDataSourceImpl
import com.adedom.main.data.providers.remote.category.CategoryRemoteDataSource
import com.adedom.main.data.providers.remote.category.CategoryRemoteDataSourceImpl
import com.adedom.main.data.providers.remote.food.FoodRemoteDataSource
import com.adedom.main.data.providers.remote.food.FoodRemoteDataSourceImpl
import com.adedom.main.data.providers.remote.profile.ProfileRemoteDataSource
import com.adedom.main.data.providers.remote.profile.ProfileRemoteDataSourceImpl
import com.adedom.main.data.repositories.AuthLogoutRepositoryImpl
import com.adedom.main.data.repositories.HomeRepositoryImpl
import com.adedom.main.data.repositories.MainCategoryRepositoryImpl
import com.adedom.main.data.repositories.UserProfileRepositoryImpl
import com.adedom.main.domain.repositories.AuthLogoutRepository
import com.adedom.main.domain.repositories.HomeRepository
import com.adedom.main.domain.repositories.MainCategoryRepository
import com.adedom.main.domain.repositories.UserProfileRepository
import com.adedom.main.domain.use_cases.GetCategoryUseCase
import com.adedom.main.domain.use_cases.GetUserProfileUseCase
import com.adedom.main.domain.use_cases.LogoutUseCase
import com.adedom.main.domain.use_cases.MainContentUseCase
import com.adedom.main.presentation.view_model.MainViewModel
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val featureMainModule = DI.Module(name = "featureMainModule") {

    // data
    bindSingleton<UserProfileLocalDataSource> { UserProfileLocalDataSourceImpl(instance()) }
    bindSingleton<CategoryLocalDataSource> { CategoryLocalDataSourceImpl(instance()) }

    bindSingleton<ProfileRemoteDataSource> {
        ProfileRemoteDataSourceImpl(
            instance(),
            instance(),
            instance(),
        )
    }
    bindSingleton<CategoryRemoteDataSource> { CategoryRemoteDataSourceImpl(instance(), instance()) }
    bindSingleton<FoodRemoteDataSource> { FoodRemoteDataSourceImpl(instance(), instance()) }
    bindSingleton<AuthRemoteDataSource> {
        AuthRemoteDataSourceImpl(
            instance(),
            instance(),
            instance(),
        )
    }

    bindSingleton<UserProfileRepository> { UserProfileRepositoryImpl(instance(), instance()) }
    bindSingleton<HomeRepository> { HomeRepositoryImpl(instance()) }
    bindSingleton<AuthLogoutRepository> { AuthLogoutRepositoryImpl(instance(), instance()) }
    bindSingleton<MainCategoryRepository> { MainCategoryRepositoryImpl(instance(), instance()) }

    // domain
    bindProvider { GetUserProfileUseCase(instance()) }
    bindProvider { MainContentUseCase(instance(), instance()) }
    bindProvider { LogoutUseCase(instance(), instance()) }
    bindProvider { GetCategoryUseCase(instance()) }

    //presentation
    bindProvider { MainViewModel(instance(), instance(), instance()) }
}