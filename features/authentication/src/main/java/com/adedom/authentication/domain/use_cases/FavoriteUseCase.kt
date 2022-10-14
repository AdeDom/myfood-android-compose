package com.adedom.authentication.domain.use_cases

import com.adedom.data.repositories.FavoriteRepository
import com.myfood.server.data.models.response.FavoriteResponse
import myfood.database.FavoriteEntity

class FavoriteUseCase(
    private val favoriteRepository: FavoriteRepository,
) {

    suspend operator fun invoke() {
        val favoriteResponse = favoriteRepository.callFavoriteAll()
        val favoriteList = mapFavoriteResponseToFavoriteEntity(favoriteResponse)
        favoriteRepository.deleteFavoriteAll()
        favoriteRepository.saveFavoriteAll(favoriteList)
    }

    private fun mapFavoriteResponseToFavoriteEntity(favoriteList: List<FavoriteResponse>?): List<FavoriteEntity> {
        return favoriteList?.map { favorite ->
            FavoriteEntity(
                favoriteId = favorite.favoriteId,
                userId = favorite.userId,
                foodId = favorite.foodId.toLong(),
                isFavorite = if (favorite.isFavorite) 1 else 0,
                isBackup = if (favorite.isBackup) 1 else 0,
                created = favorite.created,
                updated = favorite.updated,
            )
        } ?: emptyList()
    }
}