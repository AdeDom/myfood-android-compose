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
import com.adedom.main.data.repositories.AuthLogoutRepositoryImpl
import com.adedom.main.data.repositories.MainCategoryRepositoryImpl
import com.adedom.main.data.repositories.MainFoodRepositoryImpl
import com.adedom.main.data.repositories.UserProfileRepositoryImpl
import com.adedom.main.domain.repositories.AuthLogoutRepository
import com.adedom.main.domain.repositories.MainCategoryRepository
import com.adedom.main.domain.repositories.MainFoodRepository
import com.adedom.main.domain.repositories.UserProfileRepository
import com.adedom.main.domain.use_cases.GetFoodListByCategoryIdUseCase
import com.adedom.main.domain.use_cases.LogoutUseCase
import com.adedom.main.domain.use_cases.MainContentUseCase
import com.adedom.main.presentation.view_model.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val featureMainModule = module {

    // data
    single<UserProfileLocalDataSource> { UserProfileLocalDataSourceImpl(get()) }
    single<CategoryLocalDataSource> { CategoryLocalDataSourceImpl(get()) }
    single<FoodLocalDataSource> { FoodLocalDataSourceImpl(get()) }

    single<CategoryRemoteDataSource> { CategoryRemoteDataSourceImpl(get()) }
    single<FoodRemoteDataSource> { FoodRemoteDataSourceImpl(get()) }
    single<AuthRemoteDataSource> { AuthRemoteDataSourceImpl(get(), get()) }

    single<UserProfileRepository> { UserProfileRepositoryImpl(get()) }
    single<AuthLogoutRepository> { AuthLogoutRepositoryImpl(get(), get()) }
    single<MainCategoryRepository> { MainCategoryRepositoryImpl(get(), get()) }
    single<MainFoodRepository> { MainFoodRepositoryImpl(get(), get()) }

    // domain
    factory { MainContentUseCase(get(), get()) }
    factory { LogoutUseCase(get(), get()) }
    factory { GetFoodListByCategoryIdUseCase(get(), get()) }

    //presentation
    viewModel { MainViewModel(get(), get(), get()) }
}