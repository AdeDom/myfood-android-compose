package com.adedom.myfood.di.features

import com.adedom.data.providers.local.FoodLocalDataSource
import com.adedom.data.providers.local.FoodLocalDataSourceImpl
import com.adedom.data.providers.remote.FoodRemoteDataSource
import com.adedom.data.providers.remote.FoodRemoteDataSourceImpl
import com.adedom.data.repositories.FoodRepository
import com.adedom.data.repositories.FoodRepositoryImpl
import com.adedom.main.data.providers.local.category.CategoryLocalDataSource
import com.adedom.main.data.providers.local.category.CategoryLocalDataSourceImpl
import com.adedom.main.data.providers.remote.auth.AuthRemoteDataSource
import com.adedom.main.data.providers.remote.auth.AuthRemoteDataSourceImpl
import com.adedom.main.data.providers.remote.category.CategoryRemoteDataSource
import com.adedom.main.data.providers.remote.category.CategoryRemoteDataSourceImpl
import com.adedom.main.data.repositories.AuthLogoutRepositoryImpl
import com.adedom.main.data.repositories.HomeCategoryRepositoryImpl
import com.adedom.main.domain.repositories.AuthLogoutRepository
import com.adedom.main.domain.repositories.HomeCategoryRepository
import com.adedom.main.domain.use_cases.*
import com.adedom.main.presentation.view_model.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val featureMainModule = module {

    // data
    single<CategoryLocalDataSource> { CategoryLocalDataSourceImpl(get()) }
    single<FoodLocalDataSource> { FoodLocalDataSourceImpl(get()) }

    single<CategoryRemoteDataSource> { CategoryRemoteDataSourceImpl(get()) }
    single<FoodRemoteDataSource> { FoodRemoteDataSourceImpl(get()) }
    single<AuthRemoteDataSource> { AuthRemoteDataSourceImpl(get(), get()) }

    single<AuthLogoutRepository> { AuthLogoutRepositoryImpl(get(), get()) }
    single<HomeCategoryRepository> { HomeCategoryRepositoryImpl(get(), get()) }
    single<FoodRepository> { FoodRepositoryImpl(get(), get()) }

    // domain
    factory { HomeContentUseCase(get(), get()) }
    factory { GetImageProfileUseCase(get()) }
    factory { LogoutUseCase(get(), get(), get(), get(), get()) }
    factory { GetIsAuthRoleUseCase(get()) }
    factory { SaveUnAuthRoleUseCase(get()) }
    factory { GetFoodListByCategoryIdPairUseCase(get(), get()) }

    //presentation
    viewModel {
        HomeViewModel(
            homeContentUseCase = get(),
            getImageProfileUseCase = get(),
            getFoodListByCategoryIdPairUseCase = get(),
            logoutUseCase = get(),
            getIsAuthRoleUseCase = get(),
            saveUnAuthRoleUseCase = get(),
            initFavoriteWebSocketUseCase = get(),
            getIsActiveFavoriteWebSocketUseCase = get(),
            getMyFavoriteWebSocketFlowUseCase = get(),
            updateFavoriteUseCase = get(),
            getFoodListByCategoryIdUseCase = get(),
        )
    }
}