package com.adedom.domain.use_cases

import com.adedom.data.repositories.FoodRepository
import com.myfood.server.data.models.web_sockets.FavoriteWebSocketsResponse

class UpdateFavoriteUseCase(
    private val foodRepository: FoodRepository,
) {

    suspend operator fun invoke(favorite: FavoriteWebSocketsResponse?) {
        return foodRepository.updateFavoriteByFoodId(favorite)
    }
}