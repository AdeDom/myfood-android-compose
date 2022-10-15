package com.adedom.domain.use_cases

import com.adedom.data.repositories.FavoriteWebSocketRepository

class GetIsActiveFavoriteWebSocketUseCase(
    private val favoriteWebSocketRepository: FavoriteWebSocketRepository,
) {

    operator fun invoke(): Boolean {
        return favoriteWebSocketRepository.isActive()
    }
}