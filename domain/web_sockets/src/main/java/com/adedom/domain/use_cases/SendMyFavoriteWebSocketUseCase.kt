package com.adedom.domain.use_cases

import com.adedom.core.data.providers.data_store.AppDataStore
import com.adedom.core.utils.AuthRole
import com.adedom.data.repositories.FavoriteWebSocketRepository

class SendMyFavoriteWebSocketUseCase(
    private val appDataStore: AppDataStore,
    private val favoriteWebSocketRepository: FavoriteWebSocketRepository,
) {

    suspend operator fun invoke(foodId: Int?): Boolean {
        return if (foodId != null) {
            val role = appDataStore.getAuthRole()
            if (role == AuthRole.Auth) {
                favoriteWebSocketRepository.send(foodId) == Unit
            } else {
                false
            }
        } else {
            false
        }
    }
}