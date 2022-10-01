package com.adedom.myfood.di

import com.adedom.user_profile.presentation.view_model.UserProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val featureUserProfileModule = module {

    // presentation
    viewModel { UserProfileViewModel(get(), get()) }
}