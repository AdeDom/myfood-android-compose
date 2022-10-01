package com.adedom.myfood.di

import com.adedom.user_profile.domain.use_cases.FetchUserProfileUseCase
import com.adedom.user_profile.domain.use_cases.GetUserProfileUseCase
import org.koin.dsl.module

val domainUserProfileModule = module {

    // domain
    factory { FetchUserProfileUseCase(get()) }
    factory { GetUserProfileUseCase(get()) }
}