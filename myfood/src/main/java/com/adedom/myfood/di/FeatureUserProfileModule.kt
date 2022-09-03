package com.adedom.myfood.di

import com.adedom.user_profile.domain.use_cases.FetchUserProfileUseCase
import com.adedom.user_profile.domain.use_cases.GetUserProfileUseCase
import com.adedom.user_profile.presentation.view_model.UserProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val featureUserProfileModule = module {

    // domain
    factory { FetchUserProfileUseCase(get()) }
    factory { GetUserProfileUseCase(get()) }

    // presentation
    viewModel { UserProfileViewModel(get(), get()) }
}