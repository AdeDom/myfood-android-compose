package com.adedom.domain.use_cases

import com.adedom.data.repositories.FavoriteWebSocketRepository
import com.myfood.server.data.models.web_sockets.FavoriteWebSocketsResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetMyFavoriteWebSocketFlowUseCase(
    private val favoriteWebSocketRepository: FavoriteWebSocketRepository,
) {

    operator fun invoke(): Flow<FavoriteWebSocketsResponse?> {
        return favoriteWebSocketRepository.observe().map { it.result }
    }
}