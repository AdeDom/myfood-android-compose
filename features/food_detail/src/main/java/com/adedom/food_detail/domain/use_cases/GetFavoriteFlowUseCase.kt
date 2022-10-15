package com.adedom.food_detail.domain.use_cases

import com.adedom.data.repositories.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetFavoriteFlowUseCase(
    private val favoriteRepository: FavoriteRepository,
) {

    operator fun invoke(foodId: Int?): Flow<Boolean> {
        return favoriteRepository.getIsFavoriteByFoodIdFlow(
            foodId?.toLong() ?: throw Throwable("Food id is null.")
        ).map {
            it == 1L
        }
    }
}