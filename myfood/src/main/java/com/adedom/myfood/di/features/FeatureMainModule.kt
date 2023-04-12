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
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val featureMainModule = module {

    // data
    singleOf(::CategoryLocalDataSourceImpl) { bind<CategoryLocalDataSource>() }
    singleOf(::FoodLocalDataSourceImpl) { bind<FoodLocalDataSource>() }

    singleOf(::CategoryRemoteDataSourceImpl) { bind<CategoryRemoteDataSource>() }
    singleOf(::FoodRemoteDataSourceImpl) { bind<FoodRemoteDataSource>() }
    singleOf(::AuthRemoteDataSourceImpl) { bind<AuthRemoteDataSource>() }

    singleOf(::AuthLogoutRepositoryImpl) { bind<AuthLogoutRepository>() }
    singleOf(::HomeCategoryRepositoryImpl) { bind<HomeCategoryRepository>() }
    singleOf(::FoodRepositoryImpl) { bind<FoodRepository>() }

    // domain
    factoryOf(::HomeContentUseCase)
    factoryOf(::GetImageProfileUseCase)
    factoryOf(::LogoutUseCase)
    factoryOf(::GetIsAuthRoleUseCase)
    factoryOf(::SaveUnAuthRoleUseCase)
    factoryOf(::GetFoodListByCategoryIdPairUseCase)

    //presentation
    viewModelOf(::HomeViewModel)
}