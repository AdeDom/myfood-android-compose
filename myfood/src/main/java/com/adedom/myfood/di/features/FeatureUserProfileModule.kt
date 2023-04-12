package com.adedom.myfood.di.features

import com.adedom.user_profile.presentation.view_model.UserProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val featureUserProfileModule = module {

    // presentation
    viewModelOf(::UserProfileViewModel)
}