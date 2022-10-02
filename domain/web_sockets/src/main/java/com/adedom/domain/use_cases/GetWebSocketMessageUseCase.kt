package com.adedom.domain.use_cases

import com.adedom.data.repositories.WebSocketRepository

class GetWebSocketMessageUseCase(
    private val webSocketRepository: WebSocketRepository,
) {

    operator fun invoke(): String {
        return webSocketRepository.getMessage()
    }
}