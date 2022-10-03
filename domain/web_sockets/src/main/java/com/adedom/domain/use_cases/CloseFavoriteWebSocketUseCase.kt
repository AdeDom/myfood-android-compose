package com.adedom.domain.use_cases

import com.adedom.data.repositories.FavoriteWebSocketRepository

class CloseFavoriteWebSocketUseCase(
    private val favoriteWebSocketRepository: FavoriteWebSocketRepository,
) {

    suspend operator fun invoke(): Boolean {
        return favoriteWebSocketRepository.close() == Unit
    }
}