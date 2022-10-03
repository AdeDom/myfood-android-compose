package com.adedom.domain.use_cases

import com.adedom.data.repositories.FavoriteWebSocketRepository

class SendMyFavoriteWebSocketUseCase(
    private val favoriteWebSocketRepository: FavoriteWebSocketRepository,
) {

    suspend operator fun invoke(foodId: Int?): Boolean {
        return if (foodId != null) {
            favoriteWebSocketRepository.send(foodId) == Unit
        } else {
            false
        }
    }
}