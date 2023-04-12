package com.adedom.myfood.di.domain

import com.adedom.domain.use_cases.*
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val domainWebSocketModule = module {

    factoryOf(::InitFavoriteWebSocketUseCase)
    factoryOf(::GetIsActiveFavoriteWebSocketUseCase)
    factoryOf(::GetMyFavoriteWebSocketFlowUseCase)
    factoryOf(::UpdateFavoriteUseCase)
    factoryOf(::SendMyFavoriteWebSocketUseCase)
    factoryOf(::CloseFavoriteWebSocketUseCase)
    factoryOf(::GetFoodListByCategoryIdUseCase)
}