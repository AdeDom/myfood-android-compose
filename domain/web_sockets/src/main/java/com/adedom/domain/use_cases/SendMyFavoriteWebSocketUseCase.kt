package com.adedom.domain.use_cases

import com.adedom.core.data.providers.datastore.AppDataStore
import com.adedom.core.utils.AuthRole
import com.adedom.data.repositories.FavoriteWebSocketRepository

class SendMyFavoriteWebSocketUseCase(
    private val appDataStore: AppDataStore,
    private val favoriteWebSocketRepository: FavoriteWebSocketRepository,
) {

    suspend operator fun invoke(foodId: Int?) {
        if (foodId == null) {
            throw Throwable("Food id is null.")
        }

        if (appDataStore.getAuthRole() != AuthRole.Auth) {
            throw Throwable("Auth incorrect.")
        }

        favoriteWebSocketRepository.send(foodId)
    }
}