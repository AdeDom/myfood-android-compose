package com.adedom.main.di

import com.adedom.main.data.providers.local.category.CategoryLocalDataSource
import com.adedom.main.data.providers.local.category.CategoryLocalDataSourceImpl
import com.adedom.main.data.providers.local.food.FoodLocalDataSource
import com.adedom.main.data.providers.local.food.FoodLocalDataSourceImpl
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
import com.adedom.main.data.repositories.*
import com.adedom.main.domain.repositories.*
import com.adedom.main.domain.use_cases.*
import com.adedom.main.presentation.view_model.MainViewModel
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val featureMainModule = DI.Module(name = "featureMainModule") {

    // data
    bindSingleton<UserProfileLocalDataSource> { UserProfileLocalDataSourceImpl(instance()) }
    bindSingleton<CategoryLocalDataSource> { CategoryLocalDataSourceImpl(instance()) }
    bindSingleton<FoodLocalDataSource> { FoodLocalDataSourceImpl(instance()) }

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
    bindSingleton<MainFoodRepository> { MainFoodRepositoryImpl(instance()) }

    // domain
    bindProvider { GetUserProfileUseCase(instance()) }
    bindProvider { MainContentUseCase(instance(), instance(), instance()) }
    bindProvider { LogoutUseCase(instance(), instance()) }
    bindProvider { GetCategoryUseCase(instance()) }
    bindProvider { GetFoodUseCase(instance()) }

    //presentation
    bindProvider { MainViewModel(instance(), instance(), instance()) }
}