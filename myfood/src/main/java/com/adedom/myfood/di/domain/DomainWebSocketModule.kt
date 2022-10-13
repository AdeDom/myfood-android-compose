package com.adedom.myfood.di.domain

import com.adedom.domain.use_cases.*
import org.koin.dsl.module

val domainWebSocketModule = module {

    factory { InitFavoriteWebSocketUseCase(get()) }
    factory { GetIsActiveFavoriteWebSocketUseCase(get()) }
    factory { GetMyFavoriteWebSocketFlowUseCase(get()) }
    factory { UpdateFavoriteUseCase(get()) }
    factory { SendMyFavoriteWebSocketUseCase(get(), get()) }
    factory { CloseFavoriteWebSocketUseCase(get()) }
    factory { GetFoodListFlowUseCase(get()) }
}