package com.adedom.myfood.di.domain

import com.adedom.user_profile.domain.use_cases.FetchUserProfileUseCase
import com.adedom.user_profile.domain.use_cases.GetMyUserIdUseCase
import com.adedom.user_profile.domain.use_cases.GetUserProfileUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val domainUserProfileModule = module {

    // domain
    factoryOf(::FetchUserProfileUseCase)
    factoryOf(::GetUserProfileUseCase)
    factoryOf(::GetMyUserIdUseCase)
}