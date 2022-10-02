package com.adedom.myfood.di.domain

import com.adedom.domain.use_cases.GetWebSocketMessageUseCase
import org.koin.dsl.module

val domainWebSocketModule = module {

    factory { GetWebSocketMessageUseCase(get()) }
}