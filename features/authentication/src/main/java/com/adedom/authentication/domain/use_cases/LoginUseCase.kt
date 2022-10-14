package com.adedom.authentication.domain.use_cases

import com.adedom.authentication.domain.repositories.AuthLoginRepository
import com.adedom.data.repositories.FavoriteRepository
import com.adedom.user_profile.domain.use_cases.FetchUserProfileUseCase
import com.myfood.server.data.models.request.LoginRequest
import com.myfood.server.data.models.response.FavoriteResponse
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import myfood.database.FavoriteEntity

class LoginUseCase(
    private val authLoginRepository: AuthLoginRepository,
    private val favoriteRepository: FavoriteRepository,
    private val fetchUserProfileUseCase: FetchUserProfileUseCase,
) {

    suspend operator fun invoke(email: String, password: String) {
        return coroutineScope {
            val loginRequest = LoginRequest(email, password)
            val tokenResponse = authLoginRepository.callLogin(loginRequest)
                ?: throw Throwable("Error")
            authLoginRepository.saveToken(tokenResponse.accessToken, tokenResponse.refreshToken)
            authLoginRepository.saveAuthRole()

            val userProfileAsync = async { fetchUserProfileUseCase() }
            val favoriteAsync = async { favoriteRepository.callFavoriteAll() }

            userProfileAsync.await()
            val favoriteList = mapFavoriteResponseToFavoriteEntity(favoriteAsync.await())
            favoriteRepository.deleteFavoriteAll()
            favoriteRepository.saveFavoriteAll(favoriteList)
        }
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